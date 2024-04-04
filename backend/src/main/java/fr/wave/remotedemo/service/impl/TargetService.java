package fr.wave.remotedemo.service.impl;

import fr.wave.remotedemo.dto.TargetDTO;
import fr.wave.remotedemo.repository.TargetRepository;
import fr.wave.remotedemo.service.ITargetService;
import fr.wave.remotedemo.transformer.TargetTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TargetService implements ITargetService {
    private final TargetRepository targetRepository;
    private final FileService fileService;

    @Override
    public TargetDTO getTarget(Long id) {
        return TargetTransformer.toDTO(targetRepository.findById(String.valueOf(id)).orElse(null));
    }

    @Override
    public TargetDTO createTarget(TargetDTO targetDTO, MultipartFile file) {
        targetDTO.setPictureId(fileService.createFile(file).getId());
        return TargetTransformer.toDTO(targetRepository.save(TargetTransformer.toEntity(targetDTO)));
    }


}
