package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.entity.TargetEntity;
import fr.wave.remotedemo.repository.TargetRepository;
import fr.wave.remotedemo.utils.EndpointsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(EndpointsUtils.COMPETITIONS_COMPETITORS_TARGETS)
@RequiredArgsConstructor
public class UserTargetResource {
    private final TargetRepository targetRepository;

    @GetMapping()
    public TargetEntity getUserTargets(@PathVariable Long competitorId, @PathVariable Long competitionId) {
        return targetRepository.findByCompetitionIdAndCompetitorId(competitionId, competitorId);
    }

    @PostMapping()
    public TargetEntity createUserTarget(@RequestBody TargetEntity target, @PathVariable Long competitorId, @PathVariable Long competitionId) {
        target.setCompetitionId(competitionId);
        target.setCompetitorId(competitorId);
        return targetRepository.save(target);
    }
}
