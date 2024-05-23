package fr.wave.remotedemo.service.impl;

import fr.wave.remotedemo.dto.SuperBiathlonParameters;
import fr.wave.remotedemo.entity.ImpactEntity;
import fr.wave.remotedemo.entity.TargetEntity;
import fr.wave.remotedemo.enums.Event;
import fr.wave.remotedemo.enums.Zone;
import fr.wave.remotedemo.service.IResultService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResultService implements IResultService {

    public int getResult(TargetEntity target) {
        Event event = target.getEvent();
        return switch (event) {
            case PRECISION -> this.calculateScorePrecision(target);
            case BIATHLON -> this.calculateScoreBiathlon(target);
            case SUPER_BIATHLON -> this.getScoreSuperBiathlon(target);
            default -> 0;
        };
    }

    int calculateScorePrecision(TargetEntity target) {
        int score = 0;
        int impactCount = 0;
        Map<Zone, List<ImpactEntity>> impactMap = new HashMap<>();

        // Calculer le score initial en prenant les 10 meilleurs impacts
        List<ImpactEntity> impacts = getValidImpacts(new ArrayList<>(target.getImpacts()), target.getTime(), 600000, 10, 2);
        score = impacts.stream().mapToInt(ImpactEntity::getScore).sum();
        score -= 50 * (target.getShotsTooCloseCount() + target.getBadArrowExtractionsCount() + target.getTargetSheetNotTouchedCount() + (target.isDepartureSteal() ? 1 : 0) + (target.isArmedBeforeCountdown() ? 1 : 0));
        return Math.max(0, score); // Pas de score négatif
    }


    List<ImpactEntity> getValidImpacts(List<ImpactEntity> impacts,int time,int maxTime,int maximumShots,int maximumImpactsPerZone) {
        impacts = new ArrayList<>(impacts);
        impacts.sort((a, b) -> b.getScore() - a.getScore());
        // En cas de dépassement du temps, le plus mauvais tir sera refusé.
        if (time > maxTime) {
            System.out.println("Temps dépassé");
            impacts.removeLast();
        }

        // Si plus de 3 impacts sont relevés sur le plastron, le score sera amputé du ou des meilleurs impacts
        // supplémentaires.
        // Si plus de 3 tirs (déclenchements) sont comptabilisés, le score sera amputé du ou des meilleurs
        // impacts supplémentaires.
        if (impacts.size() > maximumShots) {
            HashMap<Zone, List<ImpactEntity>> impactsByZone = this.getImpactsByZone(impacts);
            int numberToRemove = impacts.size() - maximumShots;
            while (numberToRemove > 0) {
                if (this.hasMoreThanNImpactInZone(impactsByZone,maximumImpactsPerZone)) {
                    //check the impact with highest score is in the same visual
                    List<ImpactEntity> impactsCopy = new ArrayList<>(impacts);
                    for (ImpactEntity impact : impactsCopy) {
                        List<ImpactEntity> impactEntities = impactsByZone.get(impact.getZone());
                        if (impactEntities.size() > maximumImpactsPerZone && numberToRemove > 0) {
                            impactEntities.removeFirst();
                            numberToRemove--;
                        }
                    }
                    impacts = new ArrayList<>();
                    impactsByZone.values().forEach(impacts::addAll);
                } else {
                    impacts.removeFirst();
                    numberToRemove--;
                }
            }
        }

        //Si plus de 1 impact est relevé sur le même visuel, seul le meilleur impact sera retenu.
        HashMap<Zone, List<ImpactEntity>> impactsByZone = this.getImpactsByZone(impacts);
        while (this.hasMoreThanNImpactInZone(impactsByZone,maximumImpactsPerZone)) {
            impactsByZone.values().forEach((impactsList) -> {
                while (impactsList.size() > maximumImpactsPerZone) {
                    impactsList.removeLast();
                }
            });
        }
        impacts = new ArrayList<>();

        impactsByZone.values().forEach(impacts::addAll);
        return impacts;
    }


    int calculateScoreBiathlon(TargetEntity target) {
        List<ImpactEntity> impacts = this.getValidImpacts(new ArrayList<>(target.getImpacts()), target.getTime(), 600000, 5, 1);

        //Calculer le score
        int score = impacts.stream().mapToInt(ImpactEntity::getScore).sum();


        // Appliquer les pénalités
        int penaltyScore = 50;

        int penalties = (target.isDepartureSteal() ? 1 : 0) + (target.isArmedBeforeCountdown() ? 1 : 0) + target.getShotsTooCloseCount() + target.getBadArrowExtractionsCount() + target.getTargetSheetNotTouchedCount();
        penalties *= penaltyScore;

        // Time from milliseconds to seconds
        int time = (target.getTime() - (target.getTime() % 1000)) / 1000;
        return getScoreBiathlon(time, score, penalties);
    }

    public static int getScoreBiathlon(int time, int score, int penalties) {

        int timeScore = Math.max(0, (score - (time * 2)) * 3);
        return Math.max(0, timeScore - penalties);
    }


    HashMap<Zone, List<ImpactEntity>> getImpactsByZone(List<ImpactEntity> impacts) {
        HashMap<Zone, List<ImpactEntity>> impactsByZone = new HashMap<>();
        for (ImpactEntity impact : impacts) {
            if (!impactsByZone.containsKey(impact.getZone())) {
                impactsByZone.put(impact.getZone(), new ArrayList<>());
            }
            impactsByZone.get(impact.getZone()).add(impact);
        }
        for (List<ImpactEntity> impactsList : impactsByZone.values()) {
            impactsList.sort((a, b) -> b.getScore() - a.getScore());
        }
        return impactsByZone;
    }


    int getScoreSuperBiathlon(TargetEntity target) {

        int timePenalty = this.getTimePenalty(target);
        int timeBonus = 0;
        boolean isQualif = false;
        List<String> motifsDisqualification = new ArrayList<>();
        int time = (target.getTime() - (target.getTime() % 1000)) / 1000;

        if (!isQualif) {
            if (target.getTime() > 600000) {
                motifsDisqualification.add("Temps supérieur à 10 minutes");
            } else {
                // Le compétiteur doit effectuer ses 5 tirs dans la cible
                if (target.getImpacts().size() == SuperBiathlonParameters.NUMBER_OF_IMPACTS) {
                    if (!this.hasHorsPlastron(target)) {
                        // Son parcours est validé si et seulement si tous les visuels comprennent au maximum 1 impact
                        if (!this.has1ImpactMaxPerZone(target)) {
                            motifsDisqualification.add("Plus d'un impact par visuel");
                        }
                        // 3 visuels ont un impact compris dans les zones des (570 à 471). (Contrat cible)
                        if (!this.has3ImpactsInContrat(target)) {
                            motifsDisqualification.add("Nombre d'impacts dans le contrat insuffisant");
                        }

                        timeBonus = this.getTimeBonus(this.getValidImpactsSuperBiathlon(target).size());
                    } else {
                        motifsDisqualification.add("Hors plastron");
                    }
                } else {
                    if (target.getImpacts().size() < SuperBiathlonParameters.NUMBER_OF_IMPACTS) {
                        motifsDisqualification.add("Nombre de tirs insuffisant");
                    } else {
                        motifsDisqualification.add("Nombre de tirs trop élevé");
                    }
                }
            }

        }
        if (!motifsDisqualification.isEmpty()) {
            return 0;
        }
        return this.calculateScoreSuperBiathlon(time + timePenalty - timeBonus);
    }

    private int getTimeBonus(int numberOfValidImpacts) {
        return (numberOfValidImpacts - SuperBiathlonParameters.MIN_NUMBER_VALID_IMPACTS) * SuperBiathlonParameters.BASE_TIME_BONUS;
    }

    private int getTimePenalty(TargetEntity target) {
        return (
                target.getShotsTooCloseCount() +
                        target.getBadArrowExtractionsCount() +
                        target.getTargetSheetNotTouchedCount() +
                        (target.isDepartureSteal() ? 1 : 0) +
                        (target.isArmedBeforeCountdown() ? 1 : 0)
        ) * SuperBiathlonParameters.BASE_TIME_PENALTY;
    }

    private boolean has1ImpactMaxPerZone(TargetEntity target) {
//        return this.hasOnlyOneImpactPerZone(target.getImpacts().filter((impact)impact.score));
        return this.hasOnlyOneImpactPerZone(this.getValidImpactsSuperBiathlon(target));

    }

    private boolean has3ImpactsInContrat(TargetEntity target) {
        int MIN_NUMBER_VALID_IMPACTS = 3;
        List<ImpactEntity> validImpacts = this.getValidImpactsSuperBiathlon(target);
        int numberOfValidImpacts = validImpacts.size();
        return numberOfValidImpacts >= MIN_NUMBER_VALID_IMPACTS;
    }

    private boolean hasHorsPlastron(TargetEntity target) {
        return target.getImpacts().stream().anyMatch((impact) -> impact.getZone() == Zone.OFF_TARGET);
    }


    int calculateScoreSuperBiathlon(int time) {
        int referenceTime = 90;
        return Math.max(0, 5000 + 3 * (referenceTime - time));
    }

    List<ImpactEntity> getValidImpactsSuperBiathlon(TargetEntity target) {
        return target.getImpacts().stream().filter((impact) -> impact.getScore() >= 471).collect(Collectors.toList());
    }

    private boolean hasOnlyOneImpactPerZone(List<ImpactEntity> validImpacts) {
        HashMap<Zone, List<ImpactEntity>> zones = new HashMap<>();
        validImpacts.forEach((impact) -> {
            if (!zones.containsKey(impact.getZone())) {
                zones.put(impact.getZone(), new ArrayList<>());
            }
            zones.get(impact.getZone()).add(impact);
        });
        return zones.values().stream().allMatch((zone) -> zone.size() <= 1);
    }


    private boolean hasMoreThanNImpactInZone(HashMap<Zone, List<ImpactEntity>> impactsByZone,int maxImpactsInZone) {
        impactsByZone.put(Zone.OFF_TARGET, new ArrayList<>());
        return impactsByZone.values().stream().anyMatch((zone) -> zone.stream().filter((impact) -> impact.getScore() > 0).count() > maxImpactsInZone);
    }
}
