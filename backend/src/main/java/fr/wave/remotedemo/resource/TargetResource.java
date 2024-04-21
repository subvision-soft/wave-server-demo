package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.dto.FileDTO;
import fr.wave.remotedemo.dto.UploadTargetDTO;
import fr.wave.remotedemo.entity.FileEntity;
import fr.wave.remotedemo.entity.TargetEntity;
import fr.wave.remotedemo.repository.FileRepository;
import fr.wave.remotedemo.repository.TargetRepository;
import fr.wave.remotedemo.transformer.FileTransformer;
import fr.wave.remotedemo.transformer.ImpactTransformer;
import fr.wave.remotedemo.utils.EndpointsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(EndpointsUtils.COMPETITIONS_TARGETS)
@RequiredArgsConstructor
public class TargetResource {

    private final TargetRepository targetRepository;
    private final FileRepository fileRepository;
    HashMap<Long, SseEmitter> sseMap = new HashMap<>();

    @GetMapping()
    public List<TargetEntity> getTargets(@PathVariable Long competitionId) {
        return targetRepository.findByCompetitionId(competitionId);
    }

    @PostMapping
    public TargetEntity addTarget(@RequestBody UploadTargetDTO target, @PathVariable Long competitionId) {
        String base64 = target.getPictureBase64();
        byte[] bytes = java.util.Base64.getDecoder().decode(base64);
        FileDTO fileDTO = FileDTO.builder()
                .type("image/jpeg")
                .data(bytes)
                .build();
        FileEntity file = fileRepository.save(FileTransformer.toEntity(fileDTO));
        TargetEntity targetEntity = TargetEntity.builder()
                .time(target.getTime())
                .date(target.getDate())
                .competitionId(competitionId)
                .competitorId(target.getUserId())
                .pictureId(file.getId())
                .impacts(target.getImpacts().stream().map(ImpactTransformer::toEntity).collect(Collectors.toSet()))
                .build();
        TargetEntity save = targetRepository.save(targetEntity);
        sendSse(competitionId, save);
        return save;
    }

    private void sendSse(Long competitionId, TargetEntity target) {
        SseEmitter sseEmitter = sseMap.get(competitionId);
        if (sseEmitter != null) {
            try {
                sseEmitter.send(ServerSentEvent.builder(target).build());
            } catch (Exception e) {
                sseEmitter.complete();
                sseMap.remove(competitionId);
            }
        }
    }

    @GetMapping("/sse")
    public SseEmitter getSse(@PathVariable Long competitionId) {
        return sseMap.computeIfAbsent(competitionId, k -> new SseEmitter());
    }

}
