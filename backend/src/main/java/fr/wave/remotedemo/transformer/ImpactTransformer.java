package fr.wave.remotedemo.transformer;

import fr.wave.remotedemo.dto.ImpactDTO;
import fr.wave.remotedemo.entity.ImpactEntity;

public class ImpactTransformer {

    public static ImpactDTO toDTO(ImpactEntity entity) {
        return ImpactDTO.builder()
                .id(entity.getId())
                .targetId(entity.getTargetId())
                .zone(entity.getZone())
                .score(entity.getScore())
                .distance(entity.getDistance())
                .angle(entity.getAngle())
                .amount(entity.getAmount())
                .build();
    }

    public static ImpactEntity toEntity(ImpactDTO dto) {
        return ImpactEntity.builder()
                .id(dto.getId())
                .targetId(dto.getTargetId())
                .zone(dto.getZone())
                .score(dto.getScore())
                .distance(dto.getDistance())
                .angle(dto.getAngle())
                .amount(dto.getAmount())
                .build();
    }
}
