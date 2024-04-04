package fr.wave.remotedemo.service.impl;

import fr.wave.remotedemo.dto.CompetitionDTO;
import fr.wave.remotedemo.repository.CompetitionRepository;
import fr.wave.remotedemo.service.ICompetitionService;
import fr.wave.remotedemo.transformer.CompetitionTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompetitionService implements ICompetitionService {
    private final CompetitionRepository competitionRepository;
    @Override
    public CompetitionDTO createCompetition(CompetitionDTO competition) {
        return CompetitionTransformer.toDto(competitionRepository.save(CompetitionTransformer.toEntity(competition)));
    }
    @Override
    public CompetitionDTO updateCompetition(CompetitionDTO competition) {
        return CompetitionTransformer.toDto(competitionRepository.save(CompetitionTransformer.toEntity(competition)));
    }
    @Override
    public CompetitionDTO getCompetition(Long id) {
        return CompetitionTransformer.toDto(Objects.requireNonNull(competitionRepository.findById(String.valueOf(id)).orElse(null)));
    }


}
