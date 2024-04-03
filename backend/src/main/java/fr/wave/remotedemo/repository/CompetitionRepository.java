package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.document.Competition;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CompetitionRepository  extends JpaRepository<Competition,String> {

}
