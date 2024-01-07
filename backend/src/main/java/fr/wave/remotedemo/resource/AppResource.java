package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.AppConfig;
import fr.wave.remotedemo.model.EndpointsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/")
@RequiredArgsConstructor
public class AppResource {

    private final AppConfig appConfig;
    @GetMapping("${app.endpoints.is-alive}")
    public ResponseEntity<String> isAlive() {
        return ResponseEntity.ok("Server is alive");
    }

    @GetMapping("/endpoints")
    public ResponseEntity<EndpointsModel> getEndpoints() {
        return ResponseEntity.ok( this.appConfig.getEndpointsModel());
    }

}
