package fr.wave.remotedemo.dto;

import fr.wave.remotedemo.enums.Event;
import fr.wave.remotedemo.enums.Stage;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TargetDTO {
    private int time;
    private LocalDate date;

    private Long competitionId;

    private CompetitorDTO competitor;

    private Long teamId;

    private Event event;
    private Stage stage;

    private Long id;

    private String pictureId;
    private int totalScore;

    private int shotsTooCloseCount;
    private int badArrowExtractionsCount;
    private int targetSheetNotTouchedCount;
    private boolean departureSteal;
    private boolean armedBeforeCountdown;
    private boolean timeRanOut;
}
