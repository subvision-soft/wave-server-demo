package fr.wave.remotedemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_competitor")
public class CompetitionCompetitor {

    @Id
    private Long id;

    @Column(name = "competitor_id")
    private Long competitorId;

    @Column(name = "competition_id")
    private Long competitionId;
}
