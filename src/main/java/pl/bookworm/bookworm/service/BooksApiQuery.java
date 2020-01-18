package pl.bookworm.bookworm.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import pl.bookworm.bookworm.configuration.ClientCredentials;
import pl.bookworm.bookworm.model.Book;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class BooksApiQuery {

    static String APPLICATION_NAME = "ZutBookWorm";
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    private static Volumes queryGoogleBooks(JsonFactory jsonFactory, String query) throws Exception {

        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                .build();

        Books.Volumes.List volumesList = books.volumes().list(query);
        volumesList.setLangRestrict("pl");

        return volumesList.execute();
    }

    public Set<Book> getBooks(String query, boolean isAuthor) {
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

        return transformVolumeToBook(volumes);
    }

    private Set<Book> transformVolumeToBook(Volumes volumes) {
        Set<Book> booksForTakeaway = new HashSet<>();
        for (Volume item: volumes.getItems()) {
            booksForTakeaway.add(Book.builder()
                    .googleApiId(item.getId())
                    .title(item.getVolumeInfo().getTitle())
                    .description(item.getVolumeInfo().getDescription())
                    .publicationYear(2005)
                    .build());
        }

        return  booksForTakeaway;
    }
}
