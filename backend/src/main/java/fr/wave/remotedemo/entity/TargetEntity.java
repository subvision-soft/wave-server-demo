package fr.wave.remotedemo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;



@Entity
@Table(name = "targets")
@Data
@Builder
public class TargetEntity {
    private int time;
    private LocalDate date;

    private Long competitionId;

    private Long userId;
    @Id
    private Long id;




    private String pictureId;
}
