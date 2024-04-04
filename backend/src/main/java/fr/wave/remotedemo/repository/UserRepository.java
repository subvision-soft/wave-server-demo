package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.enums.Category;
import fr.wave.remotedemo.entity.CompetitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CompetitorEntity,String> {
    CompetitorEntity findByCategory(Category division);
}
