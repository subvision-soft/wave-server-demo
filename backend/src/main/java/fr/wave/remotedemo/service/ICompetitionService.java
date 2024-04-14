package fr.wave.remotedemo.service;

import fr.wave.remotedemo.dto.CompetitionDTO;

import java.util.List;

public interface ICompetitionService {
    CompetitionDTO createCompetition(CompetitionDTO competition);

    CompetitionDTO updateCompetition(CompetitionDTO competition);

    CompetitionDTO getCompetition(Long id);

    List<CompetitionDTO> getCompetitions();
}
