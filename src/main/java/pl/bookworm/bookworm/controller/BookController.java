package pl.bookworm.bookworm.controller;

import com.google.api.services.books.model.Volumes;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.model.BookReview;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.BookRepository;
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

    @GetMapping("/getAuthorBooks/{authorName}")
    public Volumes getAuthorBooks(@PathVariable("authorName") String authorName) {
        return bookService.getAuthorBooks(authorName);
    }

    @GetMapping("/getBookByBookName/{bookName}")
    public Volumes getBookByBookName(@PathVariable("bookName") String bookName) {
        return bookService.getBooksByBookName(bookName);
    }

    // TODO: move logic of getting book's info to service
    @GetMapping("/getBookInfo/{id}")
    public Book getBookInfo(@PathVariable("id") Long id) {
//        return bookRepository.findById(id).orElse(new Book());
       return temporaryMockingBookData();
    }

    @GetMapping("/getAllBookReviews/{id}")
    public Set<BookReview> getAllBookReviews(@PathVariable("id") Long id) {
//        Book requiredBook = bookRepository.findById(id).orElse(new Book());
//        return requiredBook.getBookReviews();
        return temporaryMockingBookData().getBookReviews();
    }

    @GetMapping("/getBookRate/{id}")
    public Double getBookRate(@PathVariable("id") Long id) {
//        Book requiredBook = bookRepository.findById(id).orElse(new Book());
//        return requiredBook.getBookAverageRate();
        return temporaryMockingBookData().getBookAverageRate();
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
