package fr.wave.remotedemo.service;

import fr.wave.remotedemo.dto.TargetDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ITargetService {

    TargetDTO getTarget(Long id);

    TargetDTO createTarget(TargetDTO targetDTO, MultipartFile file);
}
