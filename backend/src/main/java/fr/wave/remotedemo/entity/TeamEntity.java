package fr.wave.remotedemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teams")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long competitionId;

    @OneToMany
    @JoinTable(name = "team_competitor",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "competitor_id")
    )
    @SQLRestriction("competitor_id in (select competitor_id from competition_competitor where competition_id = ?)")
    private Set<CompetitorEntity> competitors;
}
