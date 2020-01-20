package pl.bookworm.bookworm.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.model.BookRate;
import pl.bookworm.bookworm.model.BookReview;
import pl.bookworm.bookworm.model.BookReviewWithRate;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.BookRateRepository;
import pl.bookworm.bookworm.repository.BookRepository;
import pl.bookworm.bookworm.repository.BookReviewRepository;
import pl.bookworm.bookworm.repository.UserRepository;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;

//TODO: Check if the book is in our database or not - implement the mechanic from #BW-12
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class BookService {
	UserRepository userRepository;
	BookRepository bookRepository;
	
	BookReviewRepository bookReviewRepository;
	BookRateRepository bookRateRepository;

	BooksApiQuery booksApi;


    public Set<Book> getAuthorBooks(String query) {
        return booksApi.getBooks(query, true);
    }
    public Set<Book> getBooksByBookName(String query) {
        return booksApi.getBooks(query, false);
    }

    public String addBookReview(String reviewText, Long bookId) {
		log.info("Adding review");
	
    	// todo: Get user from session
    	User user = userRepository.findById((long) 1).orElse(new User());
    	Book book = bookRepository.findById(bookId).orElse(null);
    	
    	String validation = validationProblems(book, user);
    	if (validation != null)
    		return validation;
    	
    	BookReview review = BookReview.builder()
	    						.reviewAuthor(user)
	    						.timeOfCreation(new Date())
	    						.reviewText(reviewText)
	    						.reviewBook(book)
	    						.build();
    	
    	log.info("Adding review to database");
    	review = bookReviewRepository.save(review);
    	
    	log.info("Review added succesfuly");
    	return "Review added";
    }
    
    public String addBookRate(double rate, Long bookId) {
		log.info("Adding book rating");

		// todo: Get user from session
    	User user = userRepository.findById((long) 1).orElse(new User());
    	Book book = bookRepository.findById(bookId).orElse(null);
    	
    	String validation = validationProblems(book, user);
    	if (validation != null)
    		return validation;
    	
    	BookRate rating = BookRate.builder()
	    						.rateAuthor(user)
	    						.timeOfCreation(new Date())
	    						.rate(rate)
	    						.ratedBook(book)
	    						.build();
    	
    	log.info("Adding rate to database");
    	rating = bookRateRepository.save(rating);
    	
    	
    	log.info("Rate added succesfuly");
    	
    	updateBookAverageRate(book);
    	return "Rate added";
    }
    
    private String validationProblems(Book book, User user) {
    	if (book == null) {
			log.info("Book not found in database");
			return "Wrong book id";
    	}
    	
    	if (bookReviewRepository.findByReviewAuthorAndReviewBook(user, book) != null) {
			log.info("User {} already has reviewed or rated book {}", user.getUsername(), book.getTitle());
			return "User already has reviewed or rated book";
    	}
    	
    	return null;
    }
    
    private void updateBookAverageRate(Book book) {
    	double average = bookRateRepository.getAverageRatingByBook(book);
    	
    	book.setBookAverageRate(average);
    	book = bookRepository.save(book);
    }

	// BW-56 - create services
	public Book getBookInfo(Long id) {
        return bookRepository.findById(id).orElse(new Book());
	}

	// BW-56 - create services
	public Set<BookReviewWithRate> getAllBookReviews(Long id) {
        Book requiredBook = bookRepository.findById(id).orElse(new Book());
        return bookReviewRepository.findAllBookReviewsWithRatings(requiredBook);
	}

	// BW-56 - create services
	public Double getBookRate(Long id) {
        Book requiredBook = bookRepository.findById(id).orElse(new Book());
        return requiredBook.getBookAverageRate();
	}
}