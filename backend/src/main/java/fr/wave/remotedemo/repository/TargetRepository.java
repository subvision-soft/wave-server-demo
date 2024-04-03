package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.document.Target;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetRepository extends JpaRepository<Target, String> {
}
