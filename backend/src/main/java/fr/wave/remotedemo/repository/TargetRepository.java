package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.document.Target;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TargetRepository extends MongoRepository<Target, String> {
}
