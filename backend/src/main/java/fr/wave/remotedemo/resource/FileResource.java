package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.dto.FileDTO;
import fr.wave.remotedemo.service.impl.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/files")
@RequiredArgsConstructor
@RestController
@CrossOrigin
public class FileResource {

    private final FileService fileService;

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDTO fileDTO = fileService.getFile(id);
        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDTO.getId()+ "\"")
                .body(fileDTO.getData());
    }
}
