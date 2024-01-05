package fr.wave.remotedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WaveRemoteDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaveRemoteDemoApplication.class, args);
    }

}
