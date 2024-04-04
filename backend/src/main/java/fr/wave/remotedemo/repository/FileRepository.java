package fr.wave.remotedemo.repository;

import fr.wave.remotedemo.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {
}
