package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.entity.CompetitorEntity;
import fr.wave.remotedemo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserRepository userRepository;
    @PostMapping()
    public CompetitorEntity createUser(@RequestBody @Valid CompetitorEntity competitor) {
        return userRepository.save(competitor);
    }

    @GetMapping()
    public List<CompetitorEntity> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public CompetitorEntity getUser(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }

}
