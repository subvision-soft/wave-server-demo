package fr.wave.remotedemo.document;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Competition {
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
    private Set<Competitor> competitors;
}
