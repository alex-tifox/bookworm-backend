package pl.bookworm.bookworm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bookworm.bookworm.model.Author;
import pl.bookworm.bookworm.model.Book;

import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select b from Book b where b.author_id = ?1")
    Set<Book> findBooksByAuthorId(Long authorId);

    Author findAuthorByName(String authorName);
}
