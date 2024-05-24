package fr.wave.remotedemo.resource;

import fr.wave.remotedemo.dto.FileDTO;
import fr.wave.remotedemo.dto.TargetDTO;
import fr.wave.remotedemo.dto.UploadTargetDTO;
import fr.wave.remotedemo.entity.CompetitorEntity;
import fr.wave.remotedemo.entity.FileEntity;
import fr.wave.remotedemo.entity.ImpactEntity;
import fr.wave.remotedemo.entity.TargetEntity;
import fr.wave.remotedemo.repository.FileRepository;
import fr.wave.remotedemo.repository.ImpactRepository;
import fr.wave.remotedemo.repository.TargetRepository;
import fr.wave.remotedemo.service.IResultService;
import fr.wave.remotedemo.service.IWSService;
import fr.wave.remotedemo.transformer.FileTransformer;
import fr.wave.remotedemo.transformer.ImpactTransformer;
import fr.wave.remotedemo.transformer.TargetTransformer;
import fr.wave.remotedemo.utils.EndpointsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin
@RequestMapping(EndpointsUtils.COMPETITIONS_TARGETS)
@RequiredArgsConstructor
public class CompetitionTargetResource {


    private final IWSService wsService;
    private final TargetRepository targetRepository;
    private final FileRepository fileRepository;
    private final ImpactRepository impactRepository;
    private final IResultService resultService;
    HashMap<Long, SseEmitter> sseMap = new HashMap<>();

    @GetMapping()
    public List<TargetDTO> getTargets(@PathVariable Long competitionId) {
        return  targetRepository.findByCompetitionId(competitionId).stream().map(TargetTransformer::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TargetDTO getTarget(@PathVariable Long id) {
        return TargetTransformer.toDTO(targetRepository.findById(id.toString()).orElse(null));
    }

    @PostMapping
    public TargetDTO addTarget(@RequestBody UploadTargetDTO target, @PathVariable Long competitionId) {
        String base64 = target.getPictureBase64();
        base64 = base64.replace("data:image/jpeg;base64,", "");
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
                .competitor(CompetitorEntity.builder().id(target.getCompetitorId()).build())
                .pictureId(file.getId())
                .event(target.getEvent())
                .shotsTooCloseCount(target.getShotsTooCloseCount())
                .badArrowExtractionsCount(target.getBadArrowExtractionsCount())
                .targetSheetNotTouchedCount(target.getTargetSheetNotTouchedCount())
                .departureSteal(target.isDepartureSteal())
                .armedBeforeCountdown(target.isArmedBeforeCountdown())
                .timeRanOut(target.isTimeRanOut())
                .stage(target.getStage())
                .build();
        TargetEntity save = targetRepository.save(targetEntity);
        List<ImpactEntity> impactsEntities = target.getImpacts().stream().map(ImpactTransformer::toEntity).peek(impactEntity -> impactEntity.setTargetId(save.getId())).collect(Collectors.toList());
        impactRepository.saveAll(impactsEntities);

        save.setImpacts(new HashSet<>(impactsEntities));
        save.setTotalScore(resultService.getResult(save));
        targetRepository.save(save);
        wsService.sendUpdateTargets(competitionId.toString(), save);
        return TargetTransformer.toDTO(save);
    }

    private void sendSse(Long competitionId, TargetEntity target) {
        SseEmitter sseEmitter = sseMap.get(competitionId);
        if (sseEmitter != null) {
            try {
                System.out.println("Sending sse");
                sseEmitter.send(target.getId().toString());
            } catch (Exception e) {
                System.out.println("Error sending sse");
                sseEmitter.complete();
                sseMap.remove(competitionId);
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No sse to send");
        }
    }

    @GetMapping("/sse")
    public SseEmitter getSse(@PathVariable Long competitionId) {
        return sseMap.computeIfAbsent(competitionId, k -> new SseEmitter(0L));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTarget(@PathVariable Long id) {
        targetRepository.deleteById(String.valueOf(id));
    }



}
