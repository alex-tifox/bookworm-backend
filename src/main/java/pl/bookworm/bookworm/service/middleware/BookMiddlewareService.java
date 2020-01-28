package pl.bookworm.bookworm.service.middleware;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.dialect.pagination.FirstLimitHandler;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import pl.bookworm.bookworm.model.Author;
import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.repository.BookRepository;

import java.util.ArrayList;
import java.util.HashSet;
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
        booksApi.getBooks(query, true);
        return new HashSet<>();
    }

    public Set<Book> getBooksByName(String query) {
        // check book existing in repo - if no existing - go to google and save it in our db and return to the user
        // otherwise - return what you've found in db
       Set<Book> books = bookRepository.findBooksByTitleContainingIgnoreCase(query);
       log.info(books.toString());
       
       if (books.isEmpty()) {
           log.info("books is empty, going to Google API");
           // Go to google api: get , save and return
           Set<Pair<Book, Author>> booksWithAuthors = booksApi.getBooks(query, false);

           log.warn("Books for saving");
           log.info(books.toString());

           saveAllBooks(booksWithAuthors);

           return bookRepository.findBooksByTitleContainingIgnoreCase(query);
       }
       return books;
    }

    private void saveAllBooks(Set<Pair<Book, Author>> booksWithAuthorsToSave) {
        for (Pair<Book, Author> bookWithAuthor: booksWithAuthorsToSave) {
            bookRepository.insertBook(
                    bookWithAuthor.getSecond().getName(),
                    bookWithAuthor.getFirst().getPublicationYear(),
                    bookWithAuthor.getFirst().getTitle(),
                    bookWithAuthor.getFirst().getIsbn(),
                    bookWithAuthor.getFirst().getDescription(),
                    bookWithAuthor.getFirst().getThumbnailUrl(),
                    bookWithAuthor.getFirst().getCategories(),
                    bookWithAuthor.getFirst().getGoogleApiId());
        }
    }
}
