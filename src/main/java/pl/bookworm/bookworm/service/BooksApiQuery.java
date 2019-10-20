package pl.bookworm.bookworm.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volumes;
import org.springframework.stereotype.Service;
import pl.bookworm.bookworm.configuration.ClientCredentials;

@Service
public class BooksApiQuery {

    private static final String APPLICATION_NAME = "ZutBookWorm";

    public static Volumes queryGoogleBooks(JsonFactory jsonFactory, String query) throws Exception {

        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                .build();

        Books.Volumes.List volumesList = books.volumes().list(query);
        volumesList.setFilter("ebooks");

        Volumes volumes = volumesList.execute();

        return volumes;
    }
}
