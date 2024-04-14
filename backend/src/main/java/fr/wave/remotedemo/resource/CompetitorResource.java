package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.utils.EndpointsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(EndpointsUtils.COMPETITIONS_COMPETITORS)
@RequiredArgsConstructor
public class CompetitorResource {

}
