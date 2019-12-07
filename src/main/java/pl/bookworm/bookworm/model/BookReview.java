package pl.bookworm.bookworm.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String reviewText;

    private User reviewAuthor;

    private Date timeOfCreation;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public User getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(User reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public Date getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(Date timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
}
