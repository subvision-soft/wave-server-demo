package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.entity.CompetitorEntity;
import fr.wave.remotedemo.repository.CompetitorRepository;
import fr.wave.remotedemo.utils.EndpointsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(EndpointsUtils.COMPETITORS)
@RequiredArgsConstructor
public class CompetitorResource {

    private final CompetitorRepository competitorRepository;


    @GetMapping()
    public List<CompetitorEntity> getCompetitors() {
        return competitorRepository.findAll();
    }

    @PostMapping()
    public CompetitorEntity createCompetitor(@RequestBody CompetitorEntity competitor) {
        return competitorRepository.save(competitor);
    }

    @DeleteMapping("/{id}")
    public void deleteCompetitor(@PathVariable Long id) {
        competitorRepository.deleteById(String.valueOf(id));
    }

}
