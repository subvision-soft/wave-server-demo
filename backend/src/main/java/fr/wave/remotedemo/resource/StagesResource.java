package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.dto.EnumDTO;
import fr.wave.remotedemo.enums.Category;
import fr.wave.remotedemo.enums.Stage;
import fr.wave.remotedemo.transformer.EnumTransformer;
import fr.wave.remotedemo.utils.EndpointsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = {EndpointsUtils.COMPETITIONS_STAGES, EndpointsUtils.STAGES})
@RequiredArgsConstructor
public class StagesResource {

    @GetMapping()
    public List<EnumDTO> getStages() {
        return Arrays.stream(Stage.values()).map(EnumTransformer::toDTO).toList();
    }
}