package fr.wave.remotedemo.transformer;

import fr.wave.remotedemo.dto.FileDTO;
import fr.wave.remotedemo.entity.FileEntity;

public class FileTransformer {

    public static FileDTO toDto(FileEntity file) {
        return new FileDTO(file.getId(), file.getType(), file.getData());
    }

    public static FileEntity toEntity(FileDTO file) {
        return FileEntity.builder()
                .id(file.getId())
                .type(file.getType())
                .data(file.getData())
                .build();
    }
}
