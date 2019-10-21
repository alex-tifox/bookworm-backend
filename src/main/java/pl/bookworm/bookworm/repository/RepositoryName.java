package pl.bookworm.bookworm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookworm.bookworm.model.Model;

@Repository
public interface RepositoryName extends JpaRepository<Model, Long> {
}
