package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.entity.TargetEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebsocketResource {
    @MessageMapping("/competitions/{competitionId}/targets/update")
    @SendTo("/competitions/{competitionId}/targets/update")
    public TargetEntity sendUpdateTargets(
            @DestinationVariable @PathVariable String competitionId,
            TargetEntity targetEntity
    ) {
        return targetEntity;
    }
}
