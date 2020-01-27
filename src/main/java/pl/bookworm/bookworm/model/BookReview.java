package pl.bookworm.bookworm.model;

import lombok.*;

import javax.persistence.*;

import java.util.Date;

@Builder(toBuilder = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

    long upvotes;
    long downvotes;
    
    String reviewText;
    Date timeOfCreation;

    @ManyToOne 
    User reviewAuthor;
    
    @ManyToOne 
    Book reviewBook;
}
