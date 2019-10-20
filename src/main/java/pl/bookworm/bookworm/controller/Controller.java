package pl.bookworm.bookworm.controller;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.model.Volumes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.bookworm.bookworm.service.BooksApi;

@RestController
public class Controller {

    @GetMapping("/getAnyBook/{query}")
    public Volumes getAnyBook(@PathVariable("query") String query) {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        query = "inauthor:" + query;
        Volumes volumes = new Volumes();
        try {
            volumes = BooksApi.queryGoogleBooks(jsonFactory, query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return volumes;
    }
}
