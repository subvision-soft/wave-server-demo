package fr.wave.remotedemo.service;

import fr.wave.remotedemo.entity.TargetEntity;

public interface IWSService {
    void sendUpdateTargets(String competitionId, TargetEntity targetEntity);

}
