package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.document.Target;
import fr.wave.remotedemo.repository.TargetRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/targets")
@RequiredArgsConstructor
public class TargetResource {

    private final TargetRepository targetRepository;

    @PostMapping()
    public Target createTarget(@RequestBody @Valid Target target) {
        return targetRepository.save(target);
    }

    @GetMapping()
    public List<Target> getTargets() {
        return targetRepository.findAll();
    }


}
