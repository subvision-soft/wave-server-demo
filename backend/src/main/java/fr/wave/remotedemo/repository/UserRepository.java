package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.document.Category;
import fr.wave.remotedemo.document.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Competitor,String> {
    Competitor findByCategory(Category division);
}
