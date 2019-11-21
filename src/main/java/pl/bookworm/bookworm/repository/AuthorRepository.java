package pl.bookworm.bookworm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookworm.bookworm.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
