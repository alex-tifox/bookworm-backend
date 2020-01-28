package pl.bookworm.bookworm.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.bookworm.bookworm.model.Author;
import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.model.BookReview;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.AuthorRepository;
import pl.bookworm.bookworm.repository.BookRepository;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class AuthorService {

	AuthorRepository authorRepository;

	public Set<Book> getAuthorBooks(String authorName){
		Author foundAuthor = authorRepository.findAuthorByName(authorName);
		return authorRepository.findBooksByAuthorId(foundAuthor.getId());
	}
	
	public Set<Book> getAuthorTopBooks(String authorName){
		Author foundAuthor = authorRepository.findAuthorByName(authorName);
		ArrayList<Book> books = new ArrayList<Book>(authorRepository.findBooksByAuthorId(foundAuthor.getId()));
		Collections.sort(books, Comparator.comparing(Book::getBookAverageRate).reversed());
		return new HashSet<>(books.subList(0, 6));
	}
	
	public Author getAuthor(String authorName) {
		return authorRepository.findAuthorByName(authorName);
	}
	
	public String getAuthorBooksLastReview(String authorName) {
		return temporaryMockingAuthorData().
				getBooks().iterator().next()
				.getBookReviews().iterator().next().getReviewText();
	}
	
	static Author temporaryMockingAuthorData() {		
		return Author.builder()
				.name("Adam Mickiewicz")
				.birthplace("Nowogródek")
				.description("Writer")
				.books(temporaryMockingAuthorBooks())
				.build();
	} 
	
	static Set<Book> temporaryMockingAuthorBooks(){
		Set<Book> books = new HashSet<>();

		for (int i = 0; i < 10; i++) {
			books.add(
				Book.builder()
					.title("Dziady " + (i+2))
					.publicationYear(1822+i)
					.description("This book is veeery interesting")
					.bookAverageRate((double)i)
					.bookReviews(temporaryMockingAuthorBookReviews(i))
					.build()
					);
		}
		
		return books;
	}
	
	static Set<BookReview> temporaryMockingAuthorBookReviews(int daysSinceReview){
        Set<BookReview> bookReviews = new HashSet<>();
        
        User user = User.builder()
                .username("review_username")
                .build();
        
        bookReviews.add(
                BookReview.builder()
                        .reviewText("Awesome book!")
                        .reviewAuthor(user)
                        .timeOfCreation(Date.from(new Date().toInstant().plus(-daysSinceReview, ChronoUnit.DAYS)))
                        .build()
        );
        
        return bookReviews;

	}
}