package pl.bookworm.bookworm.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String reviewText;

    @ManyToOne
    private User reviewAuthor;

    private Date timeOfCreation;

    public BookReview() {}
    public BookReview(String reviewText, User reviewAuthor, Date timeOfCreation) {
        this.reviewText = reviewText;
        this.reviewAuthor = reviewAuthor;
        this.timeOfCreation = timeOfCreation;
    }

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
