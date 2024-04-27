package fr.wave.remotedemo.dto;

import fr.wave.remotedemo.enums.Zone;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ImpactDTO {
    private float distance;
    private int score;

    private float angle;

    private int amount;

    private Zone zone;

    private Long targetId;
    private Long id;




}
