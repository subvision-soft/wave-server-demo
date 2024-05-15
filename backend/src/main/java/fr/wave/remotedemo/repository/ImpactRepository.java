package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.entity.ImpactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpactRepository extends JpaRepository<ImpactEntity, String> {
}
