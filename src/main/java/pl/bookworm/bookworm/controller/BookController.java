package pl.bookworm.bookworm.controller;

import com.google.api.services.books.model.Volumes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.bookworm.bookworm.service.BookService;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAuthorBooks/{authorName}")
    public Volumes getAuthorBooks(@PathVariable("authorName") String authorName) {
        return bookService.getAuthorBooks(authorName);
    }

    @GetMapping("/getBookByBookName/{bookName}")
    public Volumes getBookByBookName(@PathVariable("bookName") String bookName) {

        return bookService.getBooksByBookName(bookName);
    }
}
