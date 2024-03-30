package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.document.User;
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
    public User createUser(@RequestBody @Valid User user) {
        return userRepository.save(user);
    }

    @GetMapping()
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }

}
