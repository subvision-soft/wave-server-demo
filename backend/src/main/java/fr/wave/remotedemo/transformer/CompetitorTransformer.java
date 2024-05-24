package fr.wave.remotedemo.transformer;

import fr.wave.remotedemo.dto.CompetitorDTO;
import fr.wave.remotedemo.entity.CompetitorEntity;

public class CompetitorTransformer {

    public static CompetitorDTO toDto(CompetitorEntity competitor) {
        return CompetitorDTO.builder()
                .id(competitor.getId())
                .firstName(competitor.getFirstName())
                .lastName(competitor.getLastName())
                .sex(competitor.getSex())
                .category(competitor.getCategory()).build();
    }

    public static CompetitorEntity toEntity(CompetitorDTO competitor) {
        CompetitorEntity competitorEntity = new CompetitorEntity();
        if (competitor.getId() != null) {
            competitorEntity.setId(competitor.getId());
        }
        competitorEntity.setFirstName(competitor.getFirstName());
        competitorEntity.setLastName(competitor.getLastName());
        competitorEntity.setCategory(competitor.getCategory());
        return competitorEntity;
    }
}
