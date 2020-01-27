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
        // check book existing in repo - if no existing - go to google and save it in our db and return to the user
        // otherwise - return what you've found in db
       Set<Book> books = bookRepository.findBooksByTitle(query);
       if (books == null) {
           // TODO: Go to google api: get , save and return
           books = booksApi.getBooks(query, false);
           saveAllBooks(books);

           return bookRepository.findBooksByTitle(query);
       }
       return books;
    }

    private void saveAllBooks(Set<Book> booksToSave) {
        for (Book book: booksToSave) {
            bookRepository.insertBook(
                    book.getAuthorName(),
                    book.getPublicationYear(),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getDescription(),
                    book.getThumbnailUrl(),
                    book.getCategories(),
                    book.getGoogleApiId());
        }
    }
}
