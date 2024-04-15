package fr.wave.remotedemo.entity;


import fr.wave.remotedemo.enums.Category;
import fr.wave.remotedemo.enums.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Setter
@Getter
@Table(name = "competitors")
public class CompetitorEntity {

    private String firstName;
    private String lastName;
    private Category category;
    @Id
    private Long id;
    private Sex sex;

    @ManyToMany
    @JoinTable(name = "competition_competitor",
    joinColumns = @JoinColumn(name = "competitor_id"),
    inverseJoinColumns = @JoinColumn(name = "competition_id")
    )
    private Set<CompetitionEntity> competitions;


}
