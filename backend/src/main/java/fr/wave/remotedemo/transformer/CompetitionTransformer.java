package fr.wave.remotedemo.transformer;

import fr.wave.remotedemo.dto.CompetitionDTO;
import fr.wave.remotedemo.entity.CompetitionEntity;

import java.util.ArrayList;
import java.util.HashSet;

public class CompetitionTransformer {


    public static CompetitionDTO toDto(CompetitionEntity competition) {
        return CompetitionDTO.builder()
                .id(competition.getId())
                .date(competition.getDate())
                .competitors(new ArrayList<>(competition.getCompetitors()))
                .description(competition.getDescription())
                .name(competition.getName())
                .build();
    }

    public static CompetitionEntity toEntity(CompetitionDTO competition) {
        return CompetitionEntity.builder()
                .id(competition.getId())
                .date(competition.getDate())
                .competitors(new HashSet<>(competition.getCompetitors()))
                .description(competition.getDescription())
                .name(competition.getName())
                .build();
    }


}
