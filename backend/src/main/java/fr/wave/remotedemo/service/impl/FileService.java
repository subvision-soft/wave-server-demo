package fr.wave.remotedemo.service.impl;

import fr.wave.remotedemo.dto.FileDTO;
import fr.wave.remotedemo.repository.FileRepository;
import fr.wave.remotedemo.service.IFileService;
import fr.wave.remotedemo.transformer.FileTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {

    private final FileRepository fileRepository;
    @Override
    public FileDTO createFile(MultipartFile file) {
        FileDTO fileDTO = new FileDTO(file);
        return FileTransformer.toDto(fileRepository.save(FileTransformer.toEntity(fileDTO))) ;
    }

    @Override
    public FileDTO getFile(String id) {
        return FileTransformer.toDto(Objects.requireNonNull(fileRepository.findById(id).orElse(null)));
    }
}
