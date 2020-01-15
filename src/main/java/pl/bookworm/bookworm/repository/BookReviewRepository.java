package pl.bookworm.bookworm.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.model.BookReview;
import pl.bookworm.bookworm.model.BookReviewWithRate;
import pl.bookworm.bookworm.model.User;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
	BookReview findByReviewAuthorAndReviewBook(User user, Book book);
	
	@Query("Select "
	+ "new pl.bookworm.bookworm.model.BookReviewWithRate(v.Id, v.reviewAuthor, v.reviewText, t, v.timeOfCreation)"
	+ "from BookReview v left join BookRate t "
	+ "on (v.reviewBook = t.ratedBook and v.reviewAuthor = t.rateAuthor) "
	+ "where v.reviewBook = ?1")
    Set<BookReviewWithRate> findAllBookReviewsWithRatings(Book book);
}