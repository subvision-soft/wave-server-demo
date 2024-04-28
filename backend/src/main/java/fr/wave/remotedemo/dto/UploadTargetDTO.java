package fr.wave.remotedemo.dto;

import fr.wave.remotedemo.enums.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadTargetDTO {
    private int time;
    private LocalDate date;

    private Long competitionId;

    private Long userId;

    private Long id;

    private String pictureBase64;

    private List<ImpactDTO> impacts;

    private Event event;
}
