package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.config.AppConfig;
import fr.wave.remotedemo.dto.EndpointsDTO;
import fr.wave.remotedemo.dto.ServerAdressDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.DatagramSocket;
import java.net.InetAddress;

@RestController
@CrossOrigin
@RequestMapping("/")
@RequiredArgsConstructor
public class AppResource {

    private final AppConfig appConfig;

    @Value("${server.port}")
    private String port;
    @Value("${server.servlet.context-path}")
    private String contextPath;



    @GetMapping("${app.endpoints.is-alive}")
    public ResponseEntity<String> isAlive() {
        return ResponseEntity.ok("Server is alive");
    }

    @GetMapping("/endpoints")
    public ResponseEntity<EndpointsDTO> getEndpoints() {
        return ResponseEntity.ok( this.appConfig.getEndpointsModel());
    }

    @GetMapping("/adress")
    public ResponseEntity<String> getIpAndPort() {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return ResponseEntity.ok(socket.getLocalAddress().getHostAddress() + ":" + port +  contextPath);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
