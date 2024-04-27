package fr.wave.remotedemo.dto;

import fr.wave.remotedemo.entity.CompetitorEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CompetitionDTO {
    private Long id;
    private LocalDate date;
    private String description;
    private String name;

    private List<CompetitorDTO> competitors;
}
