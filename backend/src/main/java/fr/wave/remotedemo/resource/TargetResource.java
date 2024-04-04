package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.entity.TargetEntity;
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
    public TargetEntity createTarget(@RequestBody @Valid TargetEntity target) {
        return targetRepository.save(target);
    }

    @GetMapping()
    public List<TargetEntity> getTargets() {
        return targetRepository.findAll();
    }


}
