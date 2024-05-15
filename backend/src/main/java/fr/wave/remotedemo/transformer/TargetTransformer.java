package fr.wave.remotedemo.transformer;

import fr.wave.remotedemo.dto.TargetDTO;
import fr.wave.remotedemo.dto.UploadTargetDTO;
import fr.wave.remotedemo.entity.TargetEntity;

import java.util.stream.Collectors;

public class TargetTransformer {

    public static TargetDTO toDTO(TargetEntity entity) {
        return TargetDTO.builder()
                .id(entity.getId())
                .competitionId(entity.getCompetitionId())
                .date(entity.getDate())
                .time(entity.getTime())
                .userId(entity.getCompetitorId())
                .pictureId(entity.getPictureId())
                .event(entity.getEvent())
                .targetSheetNotTouchedCount(entity.getTargetSheetNotTouchedCount())
                .shotsTooCloseCount(entity.getShotsTooCloseCount())
                .badArrowExtractionsCount(entity.getBadArrowExtractionsCount())
                .departureSteal(entity.isDepartureSteal())
                .armedBeforeCountdown(entity.isArmedBeforeCountdown())
                .timeRanOut(entity.isTimeRanOut())
                .build();
    }

    public static TargetEntity toEntity(TargetDTO dto) {
        return TargetEntity.builder()
                .id(dto.getId())
                .competitionId(dto.getCompetitionId())
                .date(dto.getDate())
                .time(dto.getTime())
                .competitorId(dto.getUserId())
                .pictureId(dto.getPictureId())
                .event(dto.getEvent())
                .shotsTooCloseCount(dto.getShotsTooCloseCount())
                .badArrowExtractionsCount(dto.getBadArrowExtractionsCount())
                .targetSheetNotTouchedCount(dto.getTargetSheetNotTouchedCount())
                .departureSteal(dto.isDepartureSteal())
                .armedBeforeCountdown(dto.isArmedBeforeCountdown())
                .timeRanOut(dto.isTimeRanOut())

                .build();
    }
    public static TargetEntity toEntity(UploadTargetDTO dto, String pictureId) {
        return TargetEntity.builder()
                .id(dto.getId())
                .competitionId(dto.getCompetitionId())
                .date(dto.getDate())
                .time(dto.getTime())
                .competitorId(dto.getUserId())
                .pictureId(pictureId)
                .impacts(dto.getImpacts().stream().map(ImpactTransformer::toEntity).collect(Collectors.toSet()))
                .event(dto.getEvent())
                .shotsTooCloseCount(dto.getShotsTooCloseCount())
                .badArrowExtractionsCount(dto.getBadArrowExtractionsCount())
                .targetSheetNotTouchedCount(dto.getTargetSheetNotTouchedCount())
                .departureSteal(dto.isDepartureSteal())
                .armedBeforeCountdown(dto.isArmedBeforeCountdown())
                .timeRanOut(dto.isTimeRanOut())
                .build();
    }
}
