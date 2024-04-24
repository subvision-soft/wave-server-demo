package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.dto.CompetitionDTO;
import fr.wave.remotedemo.entity.CompetitionEntity;
import fr.wave.remotedemo.repository.CompetitionRepository;
import fr.wave.remotedemo.service.ICompetitionService;
import fr.wave.remotedemo.utils.EndpointsUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(EndpointsUtils.COMPETITIONS)
@RequiredArgsConstructor
@RestController
@CrossOrigin

public class CompetitionsResource {

    private final ICompetitionService competitionService;


    @GetMapping("/{id}/is-wave-db-alive")
    public String isAlive() {
        return "Server is alive";
    }

    @PostMapping()
    public CompetitionDTO createCompetition(@RequestBody @Valid CompetitionDTO competition) {
        return competitionService.createCompetition(competition);
    }


    @PutMapping()
    public CompetitionDTO updateCompetition(@RequestBody @Valid CompetitionDTO competition) {
        return competitionService.updateCompetition(competition);
    }

    @GetMapping("/{id}")
    public CompetitionDTO getCompetition(@PathVariable Long id) {
        return competitionService.getCompetition(id);
    }

    @GetMapping()
    public List<CompetitionDTO> getCompetitions() {
        return competitionService.getCompetitions();
    }

    @DeleteMapping("/{id}")
    public void deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
    }
}
