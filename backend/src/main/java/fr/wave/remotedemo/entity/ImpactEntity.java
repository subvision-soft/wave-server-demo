package fr.wave.remotedemo.entity;


import fr.wave.remotedemo.enums.Zone;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "impacts")
public class ImpactEntity {
    private float distance;
    private int score;

    private float angle;

    private int amount;

    private Zone zone;

    private Long targetId;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
