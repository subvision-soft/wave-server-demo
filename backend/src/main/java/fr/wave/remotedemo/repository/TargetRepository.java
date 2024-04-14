package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.entity.FileEntity;
import fr.wave.remotedemo.entity.TargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TargetRepository extends JpaRepository<TargetEntity, String> {

    List<TargetEntity> findByCompetitionId (Long competitionId);

    TargetEntity findByCompetitionIdAndCompetitorId(Long competitionId, Long competitorId);
}
