package fr.wave.remotedemo.dto;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class FileDTO {
    private String id;

    private String type;

    private byte[] data;

    public FileDTO(MultipartFile file) {
        this.type = file.getContentType();
        try {
            this.data = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
