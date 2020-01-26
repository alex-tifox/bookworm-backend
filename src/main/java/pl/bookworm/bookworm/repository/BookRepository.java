package pl.bookworm.bookworm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bookworm.bookworm.model.Book;

import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select * from books b where b.title like %?1%", nativeQuery = true)
    Set<Book> findBooksByTitle(String titleQuery);
}
