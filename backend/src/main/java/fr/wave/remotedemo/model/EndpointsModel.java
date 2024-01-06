package fr.wave.remotedemo.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EndpointsModel {

    private final String host;
    private String port;

    private String postTarget;

    private String getCompetitors;

    private String isAlive;

    private String getCompetitionName;

    private String contextPath;
}
