package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.document.Competition;
import fr.wave.remotedemo.repository.CompetitionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/competitions")
@RequiredArgsConstructor
@RestController
@CrossOrigin

public class CompetitionsResource {

    private final CompetitionRepository competitionRepository;

    @PostMapping()
    public Competition createCompetition(@RequestBody @Valid Competition competition) {
        return competitionRepository.save(competition);
    }

    @GetMapping()
    public List<Competition> getCompetitions() {
        return competitionRepository.findAll();
    }
}
