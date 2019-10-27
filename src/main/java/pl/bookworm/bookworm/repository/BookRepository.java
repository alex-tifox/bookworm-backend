package pl.bookworm.bookworm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookworm.bookworm.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}
