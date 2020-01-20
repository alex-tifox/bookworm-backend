package pl.bookworm.bookworm.service;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.model.Volumes;

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
	
	UserService userService;
	
    private JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    public Volumes getAuthorBooks(String query) {
        return getBooks(query, true);
    }

    public Volumes getBooksByBookName(String query) {
        return getBooks(query, false);
    }

    private Volumes getBooks(String query, boolean isAuthor) {
        String prefix;

        if (isAuthor) prefix = "inauthor:";
        else prefix = "intitle:";

        query = prefix + query;
        Volumes volumes = new Volumes();
        try {
            volumes = BooksApiQuery.queryGoogleBooks(jsonFactory, query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return volumes;
    }
    
    public String addBookReview(String reviewText, Long bookId) {
		log.info("Adding review");
	
    	String username = userService.readUsernameFromSecurity();
    	
    	if(username == userService.USER_NOT_LOGGED_IN)
    		return "User isn't currently logged in";
    	
    	User user = userRepository.findByUsername(username);
    	if(user == null)
    		return "User doesn't exist";
    	
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

		String username = userService.readUsernameFromSecurity();
    	
    	if(username == userService.USER_NOT_LOGGED_IN)
    		return "User isn't currently logged in";
    	
    	User user = userRepository.findByUsername(username);
    	if(user == null)
    		return "User doesn't exist";
    	
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
	public Set<BookReview> getAllBookReviews(Long id) {
        Book requiredBook = bookRepository.findById(id).orElse(new Book());
        return requiredBook.getBookReviews();
	}

	// BW-56 - create services
	public Double getBookRate(Long id) {
        Book requiredBook = bookRepository.findById(id).orElse(new Book());
        return requiredBook.getBookAverageRate();
	}
}