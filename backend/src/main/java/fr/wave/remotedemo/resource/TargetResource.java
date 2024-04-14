package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.entity.TargetEntity;
import fr.wave.remotedemo.repository.TargetRepository;
import fr.wave.remotedemo.utils.EndpointsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(EndpointsUtils.COMPETITIONS_TARGETS)
@RequiredArgsConstructor
public class TargetResource {
    private final TargetRepository targetRepository;
    @GetMapping()
    public List<TargetEntity> getTargets(@PathVariable Long competitionId) {
        return targetRepository.findByCompetitionId(competitionId);
    }
}
