package pl.bookworm.bookworm.controller;

import com.google.api.services.books.model.Volumes;
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

@RestController
@RequestMapping("books")
public class BookController {

    private BookService bookService;

    private BookRepository bookRepository;

    @Autowired
    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

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

    private Book temporaryMockingBookData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        user.setUsername("review_username");

        Book book = new Book();
        book.setBookAverageRate(4.5);
        book.setDescription("This book is veeery interesting, romantic novel");
        book.setTitle("Book Title");

        Set<BookReview> bookReviews = new HashSet<>();
        try {
            bookReviews.add(new BookReview("Awesome book!", user, format.parse("2019-07-21")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        book.setBookReviews(bookReviews);

        return book;
    }
}
