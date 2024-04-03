package fr.wave.remotedemo.document;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Impact {
    private float distance;
    private int score;

    private float angle;

    private int amount;

    private Zone zone;

    private Long targetId;
    @Id
    private Long id;

}
