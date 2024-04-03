package fr.wave.remotedemo.document;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class Target {
    private int time;
    private LocalDate date;

    private Long competitionId;

    private Long userId;
    @Id
    private Long id;
}
