package fr.wave.remotedemo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.endpoints")
public class AppProperties {
    private final String postTarget = "/target";
    private final String getCompetitors = "/competitors";
    private final String isAlive = "/alive";
    private final String getCompetitionName = "/name";
}
