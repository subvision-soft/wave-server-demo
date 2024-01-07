package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.document.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



public interface CompetitionRepository  extends MongoRepository<Competition,String> {

}
