package fr.wave.remotedemo.service.impl;

import fr.wave.remotedemo.dto.SuperBiathlonParameters;
import fr.wave.remotedemo.entity.ImpactEntity;
import fr.wave.remotedemo.entity.TargetEntity;
import fr.wave.remotedemo.enums.Event;
import fr.wave.remotedemo.enums.Zone;
import fr.wave.remotedemo.service.IResultService;

import java.util.*;
import java.util.stream.Collectors;

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
        List<ImpactEntity> impacts = new java.util.ArrayList<>(target.getImpacts());
        impacts.sort((a, b) -> b.getScore() - a.getScore());
        for (ImpactEntity impact : impacts) {
            if (impactCount >= 10) {
                break;
            }
            score += impact.getScore();
            impactCount++;

            // Grouper les impacts par visuel pour la règle des 2 meilleurs impacts par visuel
            if (!impactMap.containsKey(impact.getZone())) {
                impactMap.put(impact.getZone(), new java.util.ArrayList<>());
            }
            impactMap.get(impact.getZone()).add(impact);
        }

        // Appliquer la règle des 2 meilleurs impacts par visuel
//        for (const impacts of impactMap.values()) {
//            if (impacts.length > 2) {
//        const excessScore = impacts.slice(2).reduce((sum, impact) => sum + impact.score, 0);
//                score -= excessScore;
//            }
//        }
        for (List<ImpactEntity> impactsList : impactMap.values()) {
            if (impactsList.size() > 2) {
                int excessScore = 0;
                for (int i = 2; i < impactsList.size(); i++) {
                    excessScore += impactsList.get(i).getScore();
                }
                score -= excessScore;
            }
        }


        // Appliquer les pénalités
        if (target.isTimeRanOut()) {
            ImpactEntity worstImpact = impacts.get(impacts.size() - 1);
            score -= worstImpact.getScore();
        }
        if (impacts.size() > 10) {
            int excessScore = 0;
            for (int i = 10; i < impacts.size(); i++) {
                excessScore += impacts.get(i).getScore();
            }
            score -= excessScore;
        }
        score -= 50 * (target.getShotsTooCloseCount() + target.getBadArrowExtractionsCount() + target.getTargetSheetNotTouchedCount() + (target.isDepartureSteal() ? 1 : 0) + (target.isArmedBeforeCountdown() ? 1 : 0));
        return Math.max(0, score); // Pas de score négatif
    }


    int calculateScoreBiathlon(TargetEntity target) {
        List<ImpactEntity> impacts = new ArrayList<>(target.getImpacts());
        impacts.sort((a, b) -> b.getScore() - a.getScore());
        // En cas de dépassement du temps, le plus mauvais tir sera refusé.
        if (target.getTime() > 600000) {
            System.out.println("Temps dépassé");
            impacts.remove(impacts.size() - 1);
        }

        // Si plus de 3 impacts sont relevés sur le plastron, le score sera amputé du ou des meilleurs impacts
        // supplémentaires.
        // Si plus de 3 tirs (déclenchements) sont comptabilisés, le score sera amputé du ou des meilleurs
        // impacts supplémentaires.
        if (impacts.size() > 3) {
            HashMap<Zone, List<ImpactEntity>> impactsByZone = this.getImpactsByZone(impacts);
            int numberToRemove = impacts.size() - 3;
            while (numberToRemove > 0) {
                if (this.hasImpactOnSameVisual(impactsByZone)) {
                    //check the impact with highest score is in the same visual
                    List<ImpactEntity> impactsCopy = new ArrayList<>(impacts);
                    for (ImpactEntity impact : impactsCopy) {
                        if (impactsByZone.get(impact.getZone()).size() > 1 && numberToRemove > 0) {
                            impacts.removeFirst();
                            impactsByZone.get(impact.getZone()).removeFirst();
                            numberToRemove--;
                        }
                    }
                } else {
                    impacts.removeFirst();
                    numberToRemove--;
                }
            }
        }

        //Si plus de 1 impact est relevé sur le même visuel, seul le meilleur impact sera retenu.
        HashMap<Zone, List<ImpactEntity>> impactsByZone = this.getImpactsByZone(impacts);
        while (this.hasImpactOnSameVisual(impactsByZone)) {
            impactsByZone.values().forEach((impactsList) -> {
                while (impactsList.size() > 1) {
                    impactsList.removeLast();
                }
            });
        }
        impacts = new ArrayList<>();

        impactsByZone.values().forEach(impacts::addAll);


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


    private boolean hasImpactOnSameVisual(HashMap<Zone, List<ImpactEntity>> impactsByZone) {
        impactsByZone.put(Zone.OFF_TARGET, new ArrayList<>());
        return impactsByZone.values().stream().anyMatch((zone) -> zone.stream().filter((impact) -> impact.getScore() > 0).count() > 1);
    }
}
