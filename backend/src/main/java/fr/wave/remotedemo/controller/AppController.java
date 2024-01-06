package fr.wave.remotedemo.controller;

import fr.wave.remotedemo.AppConfig;
import fr.wave.remotedemo.model.EndpointsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@RestController
@CrossOrigin
@RequestMapping("/")
@RequiredArgsConstructor
public class AppController {

    private final AppConfig appConfig;
    @GetMapping("${app.endpoints.is-alive}")
    public ResponseEntity<String> isAlive() {
        return ResponseEntity.ok("Server is alive");
    }

    @GetMapping("/name")
    public ResponseEntity<String> getCompetitionName() {
        return ResponseEntity.ok("Remote Demo");
    }


    @GetMapping("/endpoints")
    public ResponseEntity<EndpointsModel> getEndpoints() {
        return ResponseEntity.ok( this.appConfig.getEndpointsModel());
    }


}
