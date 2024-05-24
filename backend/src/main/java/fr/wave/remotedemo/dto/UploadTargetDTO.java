package fr.wave.remotedemo.dto;

import fr.wave.remotedemo.enums.Event;
import fr.wave.remotedemo.enums.Stage;
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

    private Long competitorId;

    private Long id;

    private Stage stage;

    private String pictureBase64;

    private List<ImpactDTO> impacts;

    private Event event;
    private int shotsTooCloseCount;
    private int badArrowExtractionsCount;
    private int targetSheetNotTouchedCount;
    private boolean departureSteal;
    private boolean armedBeforeCountdown;
    private boolean timeRanOut;
}
