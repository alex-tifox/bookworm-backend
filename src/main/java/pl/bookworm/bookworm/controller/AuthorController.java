package pl.bookworm.bookworm.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.bookworm.bookworm.model.Author;
import pl.bookworm.bookworm.model.Book;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "${config.port.access.cors}")
@RestController
public class AuthorController {

    @GetMapping("/getAllAuthorBooks/{author_name}")
    public Set<Book> getAllAuthorBooks(@PathVariable("author_name") String authorName) {
        log.info("Getting all author books " + authorName);
        return new HashSet<>();
    }

    @GetMapping("/getAllAuthorBooks/{author_name}/topBooks")
    public Set<Book> getAuthorTopBooks(@PathVariable("author_name") String authorName) {
        log.info("Getting author's top 6: " + authorName);
        return new HashSet<>();
    }

    @GetMapping("/getAuthorInfo/{author_name}")
    public Author getAuthorInfo(@PathVariable("author_name") String authorName) {
        log.info("Getting author info: " + authorName);
        return new Author();
    }

    @GetMapping("getLastBookReview/{author_name}")
    public String getLastBookReview(@PathVariable("author_name") String authorName) {
        log.info("Getting author's books' last review" + authorName);
        return "Last Review";
    }
}
