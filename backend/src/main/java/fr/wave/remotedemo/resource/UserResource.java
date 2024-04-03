package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.document.Competitor;
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
    public Competitor createUser(@RequestBody @Valid Competitor competitor) {
        return userRepository.save(competitor);
    }

    @GetMapping()
    public List<Competitor> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Competitor getUser(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }

}
