package pl.bookworm.bookworm.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;

@Builder(toBuilder = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Integer publicationYear;
    String title;
    String isbn;
    String googleApiId;
    String description;
    String thumbnailUrl;
    String categories;
    Double bookAverageRate;
    String authorName;

    @OneToMany(mappedBy = "reviewBook") @JsonIgnore
    Set<BookReview> bookReviews;
    
    @ManyToOne
    Author author;
}
