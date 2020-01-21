package pl.bookworm.bookworm.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.model.BookReview;
import pl.bookworm.bookworm.model.BookReviewRate;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.model.BookReviewWithRate;
import pl.bookworm.bookworm.repository.BookRepository;
import pl.bookworm.bookworm.service.BookReviewRateService;
import pl.bookworm.bookworm.service.BookService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("books")
public class BookController {

    BookService bookService;
    BookRepository bookRepository;
    BookReviewRateService bookReviewRateService;

    @GetMapping("/getAuthorBooks/{authorName}")
    public Set<Book> getAuthorBooks(@PathVariable("authorName") String authorName) {
        return bookService.getAuthorBooks(authorName);
    }

    @GetMapping("/getBookByBookName/{bookName}")
    public Set<Book> getBookByBookName(@PathVariable("bookName") String bookName) {
        return bookService.getBooksByBookName(bookName);
    }

    @GetMapping("/getBookInfo/{id}")
    public Book getBookInfo(@PathVariable("id") Long id) {
        return bookService.getBookInfo(id);
    }

    @GetMapping("/getAllBookReviews/{id}")
    public Set<BookReviewWithRate> getAllBookReviews(@PathVariable("id") Long id) {
        return bookService.getAllBookReviews(id);
    }

    @GetMapping("/getBookRate/{id}")
    public Double getBookRate(@PathVariable("id") Long id) {
        return bookService.getBookRate(id);
    }

	@PostMapping("/addBookReview/{id}")
	public String addBookReview(@PathVariable("id") Long id, @RequestBody String reviewText) {
		return bookService.addBookReview(reviewText, id);
	}
    
    @PostMapping("/addBookReviewRating")
    public ResponseEntity<String> addBookReviewRating(@RequestBody BookReviewRate bookReviewRate) 
	{
		Pair<String, HttpStatus> newRating = bookReviewRateService.addNewRatingSecurity(bookReviewRate);
		return new ResponseEntity<>(newRating.getFirst(), newRating.getSecond());
    }
	
	@PostMapping("/addBookRate/{id}")
	public String addBookRate(@PathVariable("id") Long id, @RequestBody double rate) {
		return bookService.addBookRate(rate, id);
	}

     static Book temporaryMockingBookData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        User user = User.builder()
                .username("review_username")
                .build();

        Set<BookReview> bookReviews = new HashSet<>();
        try {
            bookReviews.add(
                    BookReview.builder()
                            .reviewText("Awesome book!")
                            .reviewAuthor(user)
                            .timeOfCreation(format.parse("2019-06-17")).build()
            );
            bookReviews.add(
                    BookReview.builder()
                            .reviewText("Good book!")
                            .reviewAuthor(user)
                            .timeOfCreation(format.parse("2019-01-05")).build()
            );
            bookReviews.add(
                    BookReview.builder()
                            .reviewText("I like it!")
                            .reviewAuthor(user)
                            .timeOfCreation(format.parse("2019-07-21")).build()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Book.builder()
                .bookAverageRate(4.5)
                .description("This book is veeery interesting, romantic novel")
                .title("Book Title")
                .bookReviews(bookReviews)
                .build();
    }

    static Book temporaryMockingBookData(double averageBookRate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        User user = User.builder()
                .username("review_username")
                .build();

        Set<BookReview> bookReviews = new HashSet<>();
        try {
            bookReviews.add(
                    BookReview.builder()
                            .reviewText("Awesome book!")
                            .reviewAuthor(user)
                            .timeOfCreation(format.parse("2019-06-17")).build()
            );
            bookReviews.add(
                    BookReview.builder()
                            .reviewText("Good book!")
                            .reviewAuthor(user)
                            .timeOfCreation(format.parse("2019-01-05")).build()
            );
            bookReviews.add(
                    BookReview.builder()
                            .reviewText("I like it!")
                            .reviewAuthor(user)
                            .timeOfCreation(format.parse("2019-07-21")).build()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Book.builder()
                .bookAverageRate(averageBookRate)
                .description("This book is veeery interesting, romantic novel")
                .title("Book Title")
                .bookReviews(bookReviews)
                .build();
    }
}
