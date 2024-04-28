package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.dto.CompetitorDTO;
import fr.wave.remotedemo.entity.CompetitionEntity;
import fr.wave.remotedemo.entity.CompetitorEntity;
import fr.wave.remotedemo.repository.CompetitionRepository;
import fr.wave.remotedemo.repository.UserRepository;
import fr.wave.remotedemo.transformer.CompetitionTransformer;
import fr.wave.remotedemo.transformer.CompetitorTransformer;
import fr.wave.remotedemo.utils.EndpointsUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public CompetitorDTO createUser(@RequestBody @Valid CompetitorEntity competitor, @PathVariable String competitionId) {
        CompetitionEntity competition = competitionRepository.findById(competitionId ).orElse(null);
        competition.getCompetitors().add(competitor);
        competitionRepository.save(competition);
        return CompetitorTransformer.toDto(userRepository.save(competitor)) ;
    }


    @PostMapping("/{id}")
    public CompetitorDTO addCompetitor(@PathVariable String id, @PathVariable String competitionId) {
        CompetitionEntity competition = competitionRepository.findById(competitionId).orElse(null);
        CompetitorEntity competitor = userRepository.findById(id).orElse(null);
        competition.getCompetitors().add(competitor);
        competitionRepository.save(competition);
        return CompetitorTransformer.toDto(competitor);
    }



    @GetMapping()
    public List<CompetitorDTO> getUsers(@PathVariable String competitionId) {
        CompetitionEntity competition = competitionRepository.findById(competitionId).orElse(null);
        return competition.getCompetitors().stream().map(CompetitorTransformer::toDto).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void removeCompetitor(@PathVariable Long id, @PathVariable String competitionId) {
        CompetitionEntity competition = competitionRepository.findById(competitionId).orElse(null);
        competition.getCompetitors().removeIf(competitor -> competitor.getId().equals(id));
        competitionRepository.save(competition);
    }

}
