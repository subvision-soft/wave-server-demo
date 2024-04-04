package fr.wave.remotedemo.dto;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TargetDTO {
    private int time;
    private LocalDate date;

    private Long competitionId;

    private Long userId;

    private Long id;

    private String pictureId;
}
