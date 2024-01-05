package fr.wave.remotedemo.model;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;

@Builder
public class EndpointsModel {
    private String HOST;

    @Value("${server.port}")
    private String PORT;

    @Value("${app.endpoints.postTarget}")
    private String POST_TARGET;


    @Value("${app.endpoints.getCompetitors}")
    private String GET_COMPETITORS;

    @Value("${app.endpoints.isAlive}")
    private String IS_ALIVE;

    @Value("${app.endpoints.getCompetitionName}")
    private String GET_COMPETITION_NAME;
}
