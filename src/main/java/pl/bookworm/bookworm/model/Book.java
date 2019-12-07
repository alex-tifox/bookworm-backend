package pl.bookworm.bookworm.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer publicationYear;
    private String title;
    private String isbn;
    private String googleApiId;
    private String description;
    private Double bookAverageRate;

    @OneToMany
    private Set<BookReview> bookReviews;

    public Book() {}
    public Book(String title, String description, Double bookAverageRate, Set<BookReview> bookReviews) {
        this.title = title;
        this.description = description;
        this.bookAverageRate = bookAverageRate;
        this.bookReviews = bookReviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGoogleApiId() {
        return googleApiId;
    }

    public void setGoogleApiId(String googleApiId) {
        this.googleApiId = googleApiId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BookReview> getBookReviews() {
        return bookReviews;
    }

    public void setBookReviews(Set<BookReview> bookReviews) {
        this.bookReviews = bookReviews;
    }

    public Double getBookAverageRate() {
        return bookAverageRate;
    }

    public void setBookAverageRate(Double bookAverageRate) {
        this.bookAverageRate = bookAverageRate;
    }
}
