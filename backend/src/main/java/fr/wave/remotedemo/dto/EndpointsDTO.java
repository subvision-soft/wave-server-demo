package fr.wave.remotedemo.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EndpointsDTO {

    private final String host;
    private String port;

    private String postTarget;

    private String getCompetitors;

    private String isAlive;

    private String getCompetitionName;

    private String contextPath;
}
