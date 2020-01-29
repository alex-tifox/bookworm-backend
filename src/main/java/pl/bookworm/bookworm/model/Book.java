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

    String publishedDate;
    @Column(columnDefinition = "TEXT")
    String title;
    String isbn;
    String googleApiId;
    @Column(columnDefinition = "TEXT")
    String description;
    @Column(columnDefinition = "TEXT")	
    String thumbnailUrl;
    String categories;
    Double bookAverageRate;

    @OneToMany(mappedBy = "reviewBook") @JsonIgnore
    Set<BookReview> bookReviews;
    
    @ManyToOne
    Author author;

    @Override
    public String toString() {
        return "Book{" +

                "publishedDate=" + publishedDate +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", googleApiId='" + googleApiId + '\'' +
                ", description='" + description + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", categories='" + categories + '\'' +
                ", bookAverageRate=" + bookAverageRate +
                ", bookReviews=" + bookReviews +
                ", author=" + author +
                '}';
    }
}
