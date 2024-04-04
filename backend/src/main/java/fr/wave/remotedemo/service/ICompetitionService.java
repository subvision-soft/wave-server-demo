package fr.wave.remotedemo.service;

import fr.wave.remotedemo.dto.CompetitionDTO;

public interface ICompetitionService {
    CompetitionDTO createCompetition(CompetitionDTO competition);

    CompetitionDTO updateCompetition(CompetitionDTO competition);

    CompetitionDTO getCompetition(Long id);
}
