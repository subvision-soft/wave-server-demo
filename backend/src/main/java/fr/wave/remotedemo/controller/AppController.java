package fr.wave.remotedemo.controller;

import fr.wave.remotedemo.model.EndpointsModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@RestController
@CrossOrigin
@RequestMapping("/")
public class AppController {
    @RequestMapping("/isAlive")
    public ResponseEntity<String> isAlive() {
        return ResponseEntity.ok("Server is alive");
    }

    @RequestMapping("/endpoints")
    public ResponseEntity<EndpointsModel> getEndpoints() throws UnknownHostException {
        return ResponseEntity.ok(getEndpointsModel());
    }

    private EndpointsModel getEndpointsModel() throws UnknownHostException {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String ip = socket.getLocalAddress().getHostAddress();
            return EndpointsModel.builder().HOST(ip).build();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
