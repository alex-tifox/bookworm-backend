package pl.bookworm.bookworm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.bookworm.bookworm.model.BookReview;
import pl.bookworm.bookworm.model.BookReviewRate;
import pl.bookworm.bookworm.model.User;

@Repository
public interface BookReviewRateRepository extends JpaRepository<BookReviewRate, Long>
{
	BookReviewRate findByBookReviewAndUser(BookReview bookReview, User user);
}
