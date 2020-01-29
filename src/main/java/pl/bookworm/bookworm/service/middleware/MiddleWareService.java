package pl.bookworm.bookworm.service.middleware;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.bookworm.bookworm.model.Author;
import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.repository.AuthorRepository;
import pl.bookworm.bookworm.repository.BookRepository;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class MiddleWareService {
	AuthorRepository authorRepository;
    BookRepository bookRepository;
    BooksApiQuery booksApi;

    public Set<Author> getAuthors (String query){
    	Set<Author> authors = authorRepository.findByNameContainingIgnoreCase(query);
    	
    	if (authors.isEmpty()) {
            log.info("Authors is empty, going to Google API");
            // Go to google api: get , save and return
            Set<Pair<Book, Author>> booksWithAuthors = booksApi.getBooks(query, true);

            saveAllAuthorsWithBooks(booksWithAuthors);

            return authorRepository.findByNameContainingIgnoreCase(query);
    	}

    	return authors;
    }
    
    public Set<Book> getAuthorBooks(String query) {
    	Author author = authorRepository.findAuthorByName(query);
    	
    	if (author == null) {
    		Set<Pair<Book, Author>> booksWithAuthors = booksApi.getBooks(query, true);
    		
    		saveAllAuthorsWithBooks(booksWithAuthors);
    		
    		author = authorRepository.findAuthorByName(query);
    		
    		// due to .getBooks() not working on first attempt
    		return bookRepository.findByAuthor(author);
    	}
    	
        return author.getBooks();
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

           saveAllAuthorsWithBooks(booksWithAuthors);

           return bookRepository.findBooksByTitleContainingIgnoreCase(query);
       }
       
       return books;
    }
    
    private void saveAllAuthorsWithBooks(Set<Pair<Book, Author>> booksWithAuthorsToSave) {
    	Set<String> authorCache = new HashSet<String>();
    	
        for (Pair<Book, Author> bookWithAuthor: booksWithAuthorsToSave) {
        	
        	// We didn't already add this author in loop
        	if (!authorCache.contains(bookWithAuthor.getSecond().getName())) {
        		authorCache.add(bookWithAuthor.getSecond().getName());
        		
        		// And he's not in our database
        		if (authorRepository.findAuthorByName(bookWithAuthor.getSecond().getName()) == null) {
        			// Save him to database
	        		Author authorInDatabase = authorRepository.save(bookWithAuthor.getSecond());
	        		
	        		// And save all his books
	        		saveAuthorsBooks(booksApi.getBooks(authorInDatabase.getName(), true), authorInDatabase);
        		}
        	}
        }
    }
    
    private void saveAuthorsBooks(Set<Pair<Book, Author>> booksWithAuthorsToSave, Author authorInDatabase) {
    	List<Book> booksToSave = new Vector<Book>();
    	
    	// Save each book, and assign it authors id
        for (Pair<Book, Author> bookWithAuthor: booksWithAuthorsToSave) {
        	bookWithAuthor.getFirst().setAuthor(authorInDatabase);
        	booksToSave.add(bookWithAuthor.getFirst());
        }
        
        bookRepository.saveAll(booksToSave);
	}
}
