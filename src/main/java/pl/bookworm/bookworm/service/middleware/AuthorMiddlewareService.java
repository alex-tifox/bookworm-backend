package pl.bookworm.bookworm.service.middleware;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.bookworm.bookworm.model.Book;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class AuthorMiddlewareService {

    BooksApiQuery booksApi;

    public Set<Book> getAuthorBooks(String query) {
        booksApi.getBooks(query, true);
        return new HashSet<>();
    }

}
