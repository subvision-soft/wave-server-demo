package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.entity.CompetitionEntity;
import fr.wave.remotedemo.entity.CompetitorEntity;
import fr.wave.remotedemo.repository.CompetitionRepository;
import fr.wave.remotedemo.repository.UserRepository;
import fr.wave.remotedemo.utils.EndpointsUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(EndpointsUtils.COMPETITIONS_COMPETITORS)
@RequiredArgsConstructor
public class CompetitionCompetitorResource {
    private final UserRepository userRepository;
    private final CompetitionRepository competitionRepository;

    @PostMapping()
    public CompetitorEntity createUser(@RequestBody @Valid CompetitorEntity competitor, @PathVariable String competitionId) {
        CompetitionEntity competition = competitionRepository.findById(competitionId ).orElse(null);
        competition.getCompetitors().add(competitor);
        competitionRepository.save(competition);
        return userRepository.save(competitor);
    }

    @GetMapping()
    public List<CompetitorEntity> getUsers(@PathVariable String competitionId) {
        CompetitionEntity competition = competitionRepository.findById(competitionId).orElse(null);
        return competition.getCompetitors().stream().toList();
    }

    @DeleteMapping("/{id}")
    public void removeCompetitor(@PathVariable Long id, @PathVariable String competitionId) {
        CompetitionEntity competition = competitionRepository.findById(competitionId).orElse(null);
        competition.getCompetitors().removeIf(competitor -> competitor.getId().equals(id));
        competitionRepository.save(competition);
    }

}
