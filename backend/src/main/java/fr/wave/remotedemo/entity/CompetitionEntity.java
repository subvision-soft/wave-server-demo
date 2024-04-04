package fr.wave.remotedemo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Table(name = "competitions")
@Builder

public class CompetitionEntity {
    @Id
    private Long id;
    private LocalDate date;
    private String description;
    private String name;

    @ManyToMany
    @JoinTable(name = "competition_competitor",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "competitor_id")
    )
    private Set<CompetitorEntity> competitors;


}
