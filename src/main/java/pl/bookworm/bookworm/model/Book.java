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
    Long authorId;

    @OneToMany(mappedBy = "reviewBook") @JsonIgnore
    Set<BookReview> bookReviews;
    
    @ManyToOne
    Author author;

    @Override
    public String toString() {
        return "Book{" +

                "publicationYear=" + publicationYear +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", googleApiId='" + googleApiId + '\'' +
                ", description='" + description + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", categories='" + categories + '\'' +
                ", bookAverageRate=" + bookAverageRate +
                ", authorName='" + authorName + '\'' +
                ", bookReviews=" + bookReviews +
                ", author=" + author +
                '}';
    }
}
