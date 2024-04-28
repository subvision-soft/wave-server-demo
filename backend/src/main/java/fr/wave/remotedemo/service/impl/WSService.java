package fr.wave.remotedemo.service.impl;

import fr.wave.remotedemo.entity.TargetEntity;
import fr.wave.remotedemo.service.IWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class WSService implements IWSService {


    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendUpdateTargets(String competitionId, TargetEntity targetEntity) {
        messagingTemplate.convertAndSend("/competitions/" + competitionId + "/targets/update", targetEntity.getId().toString());
    }
}
