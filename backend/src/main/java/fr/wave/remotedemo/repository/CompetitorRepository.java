package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.entity.CompetitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitorRepository  extends JpaRepository<CompetitorEntity, String> {

}
