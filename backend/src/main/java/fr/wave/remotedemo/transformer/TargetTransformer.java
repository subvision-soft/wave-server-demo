package fr.wave.remotedemo.transformer;

import fr.wave.remotedemo.dto.TargetDTO;
import fr.wave.remotedemo.entity.TargetEntity;

public class TargetTransformer {

    public static TargetDTO toDTO(TargetEntity entity) {
        return TargetDTO.builder()
                .id(entity.getId())
                .competitionId(entity.getCompetitionId())
                .date(entity.getDate())
                .time(entity.getTime())
                .userId(entity.getUserId())
                .pictureId(entity.getPictureId())
                .build();
    }

    public static TargetEntity toEntity(TargetDTO dto) {
        return TargetEntity.builder()
                .id(dto.getId())
                .competitionId(dto.getCompetitionId())
                .date(dto.getDate())
                .time(dto.getTime())
                .userId(dto.getUserId())
                .pictureId(dto.getPictureId())
                .build();
    }
}
