package fr.wave.remotedemo.transformer;

import fr.wave.remotedemo.dto.TargetDTO;
import fr.wave.remotedemo.dto.UploadTargetDTO;
import fr.wave.remotedemo.entity.CompetitorEntity;
import fr.wave.remotedemo.entity.TargetEntity;

import java.util.stream.Collectors;

public class TargetTransformer {

    public static TargetDTO toDTO(TargetEntity entity) {
        if (entity == null) {
            return null;
        }
        return TargetDTO.builder()
                .id(entity.getId())
                .competitionId(entity.getCompetitionId())
                .date(entity.getDate())
                .time(entity.getTime())
                .competitor(CompetitorTransformer.toDto(entity.getCompetitor()))
                .pictureId(entity.getPictureId())
                .event(entity.getEvent())
                .targetSheetNotTouchedCount(entity.getTargetSheetNotTouchedCount())
                .shotsTooCloseCount(entity.getShotsTooCloseCount())
                .badArrowExtractionsCount(entity.getBadArrowExtractionsCount())
                .departureSteal(entity.isDepartureSteal())
                .armedBeforeCountdown(entity.isArmedBeforeCountdown())
                .timeRanOut(entity.isTimeRanOut())
                .totalScore(entity.getTotalScore())
                .stage(entity.getStage())
                .build();
    }

    public static TargetEntity toEntity(TargetDTO dto) {
        return TargetEntity.builder()
                .id(dto.getId())
                .competitionId(dto.getCompetitionId())
                .date(dto.getDate())
                .time(dto.getTime())
                .competitor(CompetitorEntity.builder().id(dto.getCompetitor().getId()).build())
                .pictureId(dto.getPictureId())
                .event(dto.getEvent())
                .shotsTooCloseCount(dto.getShotsTooCloseCount())
                .badArrowExtractionsCount(dto.getBadArrowExtractionsCount())
                .targetSheetNotTouchedCount(dto.getTargetSheetNotTouchedCount())
                .departureSteal(dto.isDepartureSteal())
                .armedBeforeCountdown(dto.isArmedBeforeCountdown())
                .timeRanOut(dto.isTimeRanOut())
                .totalScore(dto.getTotalScore())
                .stage(dto.getStage())
                .build();
    }
    public static TargetEntity toEntity(UploadTargetDTO dto, String pictureId) {
        return TargetEntity.builder()
                .id(dto.getId())
                .competitionId(dto.getCompetitionId())
                .date(dto.getDate())
                .time(dto.getTime())
                .competitor(CompetitorEntity.builder().id(dto.getCompetitorId()).build())
                .pictureId(pictureId)
                .impacts(dto.getImpacts().stream().map(ImpactTransformer::toEntity).collect(Collectors.toSet()))
                .event(dto.getEvent())
                .shotsTooCloseCount(dto.getShotsTooCloseCount())
                .badArrowExtractionsCount(dto.getBadArrowExtractionsCount())
                .targetSheetNotTouchedCount(dto.getTargetSheetNotTouchedCount())
                .departureSteal(dto.isDepartureSteal())
                .armedBeforeCountdown(dto.isArmedBeforeCountdown())
                .timeRanOut(dto.isTimeRanOut())
                .stage(dto.getStage())
                .build();
    }
}
