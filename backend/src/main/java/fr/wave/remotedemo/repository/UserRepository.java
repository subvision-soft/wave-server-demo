package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
