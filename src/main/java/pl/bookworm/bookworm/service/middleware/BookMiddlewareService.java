package pl.bookworm.bookworm.service.middleware;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class BookMiddlewareService {

    BookRepository bookRepository;
    BooksApiQuery booksApi;

    public Set<Book> getAuthorBooks(String query) {
        return booksApi.getBooks(query, true);
    }

    public Set<Book> getBooksByName(String query) {
        // TODO: check book existing in repo - if no existing - go to google and save it in our db and return to the user
        // TODO: otherwise - return what you've found in db
       Set<Book> books = bookRepository.findBooksByTitle(query);
       if (books == null) {
           // TODO: Go to google api: get , save and return
           books = booksApi.getBooks(query, false);
           boolean saveResult = saveToRepository(books);

           if (saveResult) {
               return bookRepository.findBooksByTitle(query);
           }
       }
       return books;
    }

    private boolean saveToRepository(Set<Book> booksToSave) {
        List<Book> saveResult = new ArrayList<>();
        saveResult = bookRepository.saveAll(booksToSave);
        return !saveResult.isEmpty();
    }
}
