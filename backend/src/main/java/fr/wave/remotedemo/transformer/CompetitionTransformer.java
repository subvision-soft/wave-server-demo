package fr.wave.remotedemo.transformer;

import fr.wave.remotedemo.dto.CompetitionDTO;
import fr.wave.remotedemo.entity.CompetitionEntity;
import fr.wave.remotedemo.entity.CompetitorEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
        List<CompetitorEntity> competitors = competition.getCompetitors();
        if (competitors == null) {
            competitors = new ArrayList<>();
        }

        CompetitionEntity competitionEntity = new CompetitionEntity();
        if (competition.getId() != null) {
            competitionEntity.setId(competition.getId());
        }
        competitionEntity.setDate(competition.getDate());
        competitionEntity.setCompetitors(new HashSet<>(competitors));
        competitionEntity.setDescription(competition.getDescription());
        competitionEntity.setName(competition.getName());
        return competitionEntity;

    }


}
