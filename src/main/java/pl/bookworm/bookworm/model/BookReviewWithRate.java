package pl.bookworm.bookworm.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookReviewWithRate {

    Long Id;
    
    @JsonIgnoreProperties({"favouriteBook", "usersRatedBooks"})
    User reviewAuthor;
    String reviewText;
    double rate;
    Date timeOfCreation;
    long upvotes;
    long downvotes;
    
    public BookReviewWithRate(long Id, User reviewAuthor, String reviewText, BookRate rate, Date timeOfCreation, long upvotes, long downvotes) {
    	this.Id = Id;
    	this.reviewAuthor = reviewAuthor;
    	this.reviewText = reviewText;
    	this.rate = (rate != null) ? rate.getRate() : -1;
    	this.timeOfCreation = timeOfCreation;
    	this.upvotes = upvotes;
    	this.downvotes = downvotes;
    }
}
