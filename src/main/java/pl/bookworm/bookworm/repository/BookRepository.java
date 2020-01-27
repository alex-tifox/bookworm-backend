package pl.bookworm.bookworm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.bookworm.bookworm.model.Book;

import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select * from books where title like %:title%", nativeQuery = true)
    Set<Book> findBooksByTitle(@Param("title")String titleQuery);

    @Procedure(procedureName = "insert_books")
    void insertBook(@Param("AuthorName") String authorName,
                    @Param("PublicationYear") Integer publicationYear,
                    @Param("Title") String title,
                    @Param("ISBN") String isbn,
                    @Param("Description") String description,
                    @Param("ThumbnailUrl") String thumbnailUrl,
                    @Param("Categories") String categories,
                    @Param("GoogleAPI_ID") String googleApiId);


}
