package fr.wave.remotedemo.entity;


import fr.wave.remotedemo.enums.Zone;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "impacts")
public class ImpactEntity {
    private float distance;
    private int score;

    private float angle;

    private int amount;

    private Zone zone;

    private Long targetId;
    @Id
    private Long id;

}
