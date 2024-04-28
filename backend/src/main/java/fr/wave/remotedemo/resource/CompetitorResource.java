package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.dto.CompetitorDTO;
import fr.wave.remotedemo.entity.CompetitorEntity;
import fr.wave.remotedemo.repository.CompetitorRepository;
import fr.wave.remotedemo.transformer.CompetitorTransformer;
import fr.wave.remotedemo.utils.EndpointsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(EndpointsUtils.COMPETITORS)
@RequiredArgsConstructor
public class CompetitorResource {

    private final CompetitorRepository competitorRepository;


    @GetMapping()
    public List<CompetitorDTO> getCompetitors() {
        return competitorRepository.findAll().stream().map(CompetitorTransformer::toDto).toList();
    }

    @PostMapping()
    public CompetitorDTO createCompetitor(@RequestBody CompetitorEntity competitor) {
        return CompetitorTransformer.toDto(competitorRepository.save(competitor))  ;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompetitor(@PathVariable Long id) {
        competitorRepository.deleteById(String.valueOf(id));
    }

}
