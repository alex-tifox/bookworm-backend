package pl.bookworm.bookworm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.model.BookRate;
import pl.bookworm.bookworm.model.User;

@Repository
public interface BookRateRepository extends JpaRepository<BookRate, Long> {
	BookRate findByRateAuthorAndRatedBook(User user, Book book);
	
	@Query("Select avg(r.rate) from BookRate r where r.ratedBook = ?1")
	double getAverageRatingByBook(Book ratedBook);
	
	List<Book> findFirst5ByRateAuthorOrderByRateDesc(User author);
	List<Book> findFirst5ByRateAuthorOrderByTimeOfCreationDesc(User author);

}