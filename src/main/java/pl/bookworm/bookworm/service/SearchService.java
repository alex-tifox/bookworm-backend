package pl.bookworm.bookworm.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.bookworm.bookworm.model.Author;
import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.model.SearchResult;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SearchService {
	UserRepository userRepository;
	BookService bookService;
	AuthorService authorService;

	public SearchResult processSearch(String bookName, String authorName, String userName) {
		log.info("Starting proccessing search");
		
		if (bookName == null && authorName == null && userName == null) {
			log.info("Query empty");
			return SearchResult.builder().category("invalid").build();
		}
		
		if (bookName != null) {
			log.info("Book search");
			return searchBooks(bookName);
		}
		
		if (authorName != null) {
			log.info("Author search");
			return searchAuthors(authorName);
		}
		
		if (userName != null) {
			log.info("User search");
			return searchUsers(userName);
		}

		return new SearchResult();
	}
	
	SearchResult searchBooks(String bookName){
		Set<Book> booksFound = bookService.getBooksByBookName(bookName);
		log.info("Found {} books with by query for {}", booksFound.size(), bookName);
				
		return SearchResult.builder()
				.category("bookName")
				.foundBooks(booksFound)
				.build();
	}
	
	SearchResult searchAuthors(String authorName){
		Set<Author> authorsFound = authorService.findAuthorsByName(authorName);
		log.info("Found {} authors with by query for {}", authorsFound.size(), authorName);
				
		return SearchResult.builder()
				.category("authorName")
                .foundAuthors(authorsFound)
				.build();	
		}
	
	SearchResult searchUsers(String userName){
		Set<User> usersFound =	new HashSet<>(userRepository.findByUsernameContainingIgnoreCase(userName));
		log.info("Found {} users with by query for {}", usersFound.size(), userName);
				
		return SearchResult.builder()
				.category("userName")
				.foundUsers(usersFound)
				.build();
	}
}
