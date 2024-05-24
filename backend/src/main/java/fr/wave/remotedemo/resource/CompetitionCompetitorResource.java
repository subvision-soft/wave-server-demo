package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.dto.CompetitorDTO;
import fr.wave.remotedemo.entity.CompetitionEntity;
import fr.wave.remotedemo.entity.CompetitorEntity;
import fr.wave.remotedemo.enums.Event;
import fr.wave.remotedemo.enums.Stage;
import fr.wave.remotedemo.repository.CompetitionRepository;
import fr.wave.remotedemo.repository.TargetRepository;
import fr.wave.remotedemo.repository.UserRepository;
import fr.wave.remotedemo.transformer.CompetitionTransformer;
import fr.wave.remotedemo.transformer.CompetitorTransformer;
import fr.wave.remotedemo.utils.EndpointsUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(EndpointsUtils.COMPETITIONS_COMPETITORS)
@RequiredArgsConstructor
public class CompetitionCompetitorResource {
    private final UserRepository userRepository;
    private final CompetitionRepository competitionRepository;
    private final TargetRepository targetRepository;


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
    public List<CompetitorDTO> getUsers(@PathVariable String competitionId, @RequestParam(required = false)Stage stage, @RequestParam(required = false)Event event) {
        CompetitionEntity competition = competitionRepository.findById(competitionId).orElse(null);
        List<CompetitorDTO> competitors = competition.getCompetitors().stream().map(CompetitorTransformer::toDto).collect(Collectors.toList());
        if (event != null) {
            List<CompetitorDTO> competitorsWithRegisteredTargets = targetRepository.findByCompetitionId(Long.valueOf(competitionId)).stream()
                    .filter(target -> target.getEvent().equals(event) && (stage == null || target.getStage().equals(stage)))
                    .map(target -> CompetitorTransformer.toDto(target.getCompetitor()))
                    .collect(Collectors.toList());
            competitors.removeAll(competitorsWithRegisteredTargets);
        }
        return competitors;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void removeCompetitor(@PathVariable Long id, @PathVariable String competitionId) {
        CompetitionEntity competition = competitionRepository.findById(competitionId).orElse(null);
        competition.getCompetitors().removeIf(competitor -> competitor.getId().equals(id));
        competitionRepository.save(competition);
    }

}
