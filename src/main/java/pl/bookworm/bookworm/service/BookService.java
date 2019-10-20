package pl.bookworm.bookworm.service;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.model.Volumes;
import org.springframework.stereotype.Service;

//TODO: Check if the book is in our database or not - implement the mechanic from #BW-12
@Service
public class BookService {

    private JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    public Volumes getAuthorBooks(String query) {
        return getBooks(query, true);
    }

    public Volumes getBooksByBookName(String query) {
        return getBooks(query, false);
    }

    private Volumes getBooks(String query, boolean isAuthor) {
        String prefix;

        if (isAuthor) prefix = "inauthor:";
        else prefix = "intitle:";

        query = prefix + query;
        Volumes volumes = new Volumes();
        try {
            volumes = BooksApiQuery.queryGoogleBooks(jsonFactory, query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return volumes;
    }
}
