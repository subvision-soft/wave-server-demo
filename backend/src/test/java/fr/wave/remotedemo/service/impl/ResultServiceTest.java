package fr.wave.remotedemo.service.impl;

import fr.wave.remotedemo.entity.ImpactEntity;
import fr.wave.remotedemo.entity.TargetEntity;
import fr.wave.remotedemo.enums.Event;
import fr.wave.remotedemo.enums.Zone;
import fr.wave.remotedemo.service.IResultService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultServiceTest {

    private final MockID mockID = new MockID();
    private final IResultService resultService = new ResultService();

    public Set<ImpactEntity> get3ValidImpacts() {
        return new HashSet<>(
                Arrays.asList(getValidImpact(Zone.TOP_LEFT),
                        getValidImpact(Zone.TOP_RIGHT),
                        getValidImpact(Zone.BOTTOM_LEFT))

        );
    }

    public Set<ImpactEntity> get10ValidImpacts() {
        return new HashSet<>(
                Arrays.asList(
                        getValidImpact(Zone.TOP_LEFT),
                        getValidImpact(Zone.TOP_RIGHT),
                        getValidImpact(Zone.BOTTOM_LEFT),
                        getValidImpact(Zone.BOTTOM_RIGHT),
                        getValidImpact(Zone.CENTER),
                        getValidImpact(Zone.TOP_LEFT),
                        getValidImpact(Zone.TOP_RIGHT),
                        getValidImpact(Zone.BOTTOM_LEFT),
                        getValidImpact(Zone.BOTTOM_RIGHT),
                        getValidImpact(Zone.CENTER, 570)
                ));
    }

    public ImpactEntity getValidImpact(Zone zone) {
        return getValidImpact(zone, 471);
    }

    public ImpactEntity getValidImpact(Zone zone, int score) {
        mockID.getId();
        return ImpactEntity.builder()
                .id(this.mockID.getId())
                .zone(zone)
                .score(score)
                .build();
    }

    public Set<ImpactEntity> get5ValidImpacts() {
        return new HashSet<>(
                Arrays.asList(
                        getValidImpact(Zone.TOP_LEFT),
                        getValidImpact(Zone.TOP_RIGHT),
                        getValidImpact(Zone.BOTTOM_LEFT),
                        getValidImpact(Zone.BOTTOM_RIGHT, 0),
                        getValidImpact(Zone.CENTER)
                ));
    }

    @Test
    void superBiathlon_Time_higher_than_10min() {
        TargetEntity target = TargetEntity.builder()
                .impacts(get5ValidImpacts())
                .event(Event.SUPER_BIATHLON)
                .time(600001)
                .build();
        assertEquals(0, resultService.getResult(target));
        target.setTime(600000);
        assertEquals(3479, resultService.getResult(target));
    }

    @Test
    void biathlon_Time_higher_than_2min() {
        TargetEntity target = TargetEntity.builder()
                .impacts(get3ValidImpacts())
                .event(Event.BIATHLON)
                .build();
        target.setTime(600001);
        int scoreCible = 471 * 2;
        int tempsSeconde = 600;
        int score = ResultService.getScoreBiathlon(tempsSeconde, scoreCible, 0);
        assertEquals(score, resultService.getResult(target));
        target.setTime(120000);
        scoreCible = 471 * 3;
        tempsSeconde = 120;
        score = ResultService.getScoreBiathlon(tempsSeconde, scoreCible, 0);
        assertEquals(score, resultService.getResult(target));
    }

    @Test
    void superBiathlon_0_impacts() {
        TargetEntity target = TargetEntity.builder()
                .impacts(Set.of())
                .event(Event.SUPER_BIATHLON)
                .build();
        assertEquals(0, resultService.getResult(target));
    }

    @Test
    void biathlon_0_impacts() {
        TargetEntity target = TargetEntity.builder()
                .impacts(Set.of())
                .event(Event.BIATHLON)
                .build();
        assertEquals(0, resultService.getResult(target));
    }

    @Test
    void biathlon_3_impacts() {
        TargetEntity target = TargetEntity.builder()
                .impacts(get3ValidImpacts())
                .event(Event.BIATHLON)
                .build();
        target.setTime(140000);
        int scoreCible = 471 * 3;
        int tempsSeconde = 140;
        int score = ResultService.getScoreBiathlon(tempsSeconde, scoreCible, 0);
        assertEquals(score, resultService.getResult(target));
    }

    @Test
    void biathlon_multiple_impacts_same_zone() {
        TargetEntity target = TargetEntity.builder()
                .event(Event.BIATHLON)
                .impacts(new HashSet<>())
                .build();
        target.getImpacts().add(getValidImpact(Zone.BOTTOM_RIGHT, 474));
        target.getImpacts().add(getValidImpact(Zone.BOTTOM_RIGHT, 471));
        target.getImpacts().add(getValidImpact(Zone.CENTER, 471));
        target.setTime(140000);
        int scoreCible = 471 + 474;
        int tempsSeconde = 140;
        int score = ResultService.getScoreBiathlon(tempsSeconde, scoreCible, 0);
        assertEquals(score, resultService.getResult(target));
    }

    @Test
    void biathlon_penalties() {
        TargetEntity target = TargetEntity.builder()
                .event(Event.BIATHLON)
                .impacts(get3ValidImpacts())
                .build();
        target.setTime(140000);
        target.setShotsTooCloseCount(1);
        target.setBadArrowExtractionsCount(1);
        target.setTargetSheetNotTouchedCount(1);
        target.setDepartureSteal(true);
        target.setArmedBeforeCountdown(true);
        int penalties = (target.getShotsTooCloseCount() + target.getBadArrowExtractionsCount() + target.getTargetSheetNotTouchedCount() + (target.isDepartureSteal() ? 1 : 0) + (target.isArmedBeforeCountdown() ? 1 : 0)) * 50;
        int scoreCible = 471 * 3;
        int tempsSeconde = 140;
        int score = ResultService.getScoreBiathlon(tempsSeconde, scoreCible, penalties);
        assertEquals(score, resultService.getResult(target));
    }

    @Test
    void precision_0_impacts() {
        TargetEntity target = TargetEntity.builder()
                .event(Event.PRECISION)
                .impacts(Set.of())
                .build();
        assertEquals(0, resultService.getResult(target));
    }

    @Test
    void precision_more_than_10_impacts() {
        TargetEntity target = TargetEntity.builder()
                .event(Event.PRECISION)
                .impacts(get10ValidImpacts())
                .build();
        target.getImpacts().add(getValidImpact(Zone.OFF_TARGET));
        assertEquals(4239, resultService.getResult(target));
    }

    @Test
    void precision_10_impacts_but_3_in_one_zone() {
        TargetEntity target = TargetEntity.builder()
                .event(Event.PRECISION)
                .build();
        List<ImpactEntity> impacts = get10ValidImpacts().stream().collect(Collectors.toList());
        impacts.add(getValidImpact(Zone.TOP_LEFT, 570));


        impacts.remove(impacts.stream().filter((impact) -> impact.getZone() == Zone.TOP_RIGHT).findFirst().get());
        target.setImpacts(new HashSet<>(impacts));

        assertEquals(4437, resultService.getResult(target));
    }

    @Test
    void precision_11_impacts_but_3_in_one_zone() {
        TargetEntity target = TargetEntity.builder()
                .event(Event.PRECISION)
                .build();
        List<ImpactEntity> impacts = get10ValidImpacts().stream().collect(Collectors.toList());
        impacts.add(getValidImpact(Zone.TOP_LEFT, 570));
        target.setImpacts(new HashSet<>(impacts));
        assertEquals(4809, resultService.getResult(target));
    }

    @Test
    void super_biathlon_one_off_target() {
        TargetEntity target = TargetEntity.builder()
                .event(Event.SUPER_BIATHLON)
                .impacts(new HashSet<>())
                .build();
        List<ImpactEntity> impacts = get5ValidImpacts().stream().collect(Collectors.toList());
        impacts.removeFirst();
        target.setImpacts(new HashSet<>(impacts));
        target.getImpacts().add(getValidImpact(Zone.OFF_TARGET));
        assertEquals(0, resultService.getResult(target));
    }

    @Test
    void super_biathlon_5_impacts_but_same_zone() {
        TargetEntity target = TargetEntity.builder()
                .event(Event.SUPER_BIATHLON)
                .build();
        List<ImpactEntity> impacts = get3ValidImpacts().stream().collect(Collectors.toList());
        impacts.add(getValidImpact(Zone.BOTTOM_RIGHT, 570));
        impacts.add(getValidImpact(Zone.TOP_LEFT, 570));
        target.setImpacts(new HashSet<>(impacts));
        assertEquals(0, resultService.getResult(target));
    }

    @Test
    void super_biathlon_more_than_5_impacts() {
        TargetEntity target = TargetEntity.builder()
                .event(Event.SUPER_BIATHLON)
                .impacts(get10ValidImpacts())
                .build();
        assertEquals(0, resultService.getResult(target));
        target.setImpacts(get5ValidImpacts());
        assertTrue(resultService.getResult(target) > 0);
    }


    @Test
    void super_biathlon_not_enough_valid_impacts() {
        TargetEntity target = TargetEntity.builder()
                .event(Event.SUPER_BIATHLON)
                .impacts(new HashSet<>(
                        Arrays.asList(
                                getValidImpact(Zone.TOP_LEFT,468),
                                getValidImpact(Zone.TOP_RIGHT,468),
                                getValidImpact(Zone.BOTTOM_LEFT,468),
                                getValidImpact(Zone.BOTTOM_RIGHT,468),
                                getValidImpact(Zone.CENTER,471)
                        ))
                )
                .build();
        assertEquals(0, resultService.getResult(target));
        target.setImpacts(
                new HashSet<>(
                        Arrays.asList(
                                getValidImpact(Zone.TOP_LEFT,468),
                                getValidImpact(Zone.TOP_RIGHT,468),
                                getValidImpact(Zone.BOTTOM_LEFT,471),
                                getValidImpact(Zone.BOTTOM_RIGHT,471),
                                getValidImpact(Zone.CENTER,471)
                        ))
        );
        assertTrue( resultService.getResult(target)>0);
    }

    class MockID {
        private long id = 0;

        public long getId() {
            return id++;
        }
    }


}