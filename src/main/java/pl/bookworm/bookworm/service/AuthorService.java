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
import pl.bookworm.bookworm.repository.AuthorRepository;
import pl.bookworm.bookworm.repository.BookRepository;
import pl.bookworm.bookworm.service.middleware.MiddleWareService;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class AuthorService {

	AuthorRepository authorRepository;
	BookRepository bookRepository;
	
	MiddleWareService middleWareService;
	
	public Set<Author> findAuthorsByName(String name) {
		return middleWareService.getAuthors(name);
	}
	
	public Set<Book> getAuthorBooks(String authorName){
		return middleWareService.getAuthorBooks(authorName);
	}
	
	public Set<Book> getAuthorTopBooks(String authorName){
		Author foundAuthor = getAuthor(authorName);
		
		if (foundAuthor != null) {
			return bookRepository.findFirst6ByAuthorOrderByBookAverageRateDesc(foundAuthor);
		}
		
		return new HashSet<>();
	}
	
	public Author getAuthor(String authorName) {
		Author author = authorRepository.findAuthorByName(authorName);
		
		if (author == null) {
			middleWareService.getAuthors(authorName);
			author = authorRepository.findAuthorByName(authorName);
		}
		
		return author;
	}
		
	public String getAuthorBooksLastReview(String authorName) {
		return null;
	}
}