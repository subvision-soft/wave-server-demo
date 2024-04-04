package fr.wave.remotedemo.service;

import fr.wave.remotedemo.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    FileDTO createFile(MultipartFile file);

    FileDTO getFile(String id);
}
