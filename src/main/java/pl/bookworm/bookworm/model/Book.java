package pl.bookworm.bookworm.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder(toBuilder = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public")
    @SequenceGenerator(name="public", sequenceName = "book_seq")
    Long id;

    Integer publicationYear;
    String title;
    String isbn;
    String googleApiId;
    String description;
    Double bookAverageRate;

    @OneToMany
    Set<BookReview> bookReviews;
}
