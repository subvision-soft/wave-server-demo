package fr.wave.remotedemo.utils;

public class EndpointsUtils {
    public static final String COMPETITIONS = "/competitions";
    public static final String COMPETITIONS_COMPETITORS = COMPETITIONS +  "/{competitionId}/competitors";
    public static final String COMPETITIONS_COMPETITORS_TARGETS = COMPETITIONS_COMPETITORS + "/{competitorId}/targets";
    public static final String COMPETITIONS_TARGETS = COMPETITIONS + "/{competitionId}/targets";
    public static final String COMPETITORS = "/competitors";
}
