package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.entity.CompetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CompetitionRepository extends JpaRepository<CompetitionEntity,String> {

}
