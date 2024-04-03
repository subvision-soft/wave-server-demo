package fr.wave.remotedemo.document;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Setter
@Getter
public class Competitor {

    private String firstName;
    private String lastName;
    private Category category;
    @Id
    private Long id;

    @ManyToMany
    @JoinTable(name = "competition_competitor",
    joinColumns = @JoinColumn(name = "competitor_id"),
    inverseJoinColumns = @JoinColumn(name = "competition_id")
    )
    private Set<Competition> competitions;


}
