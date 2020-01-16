package pl.bookworm.bookworm.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class BookRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

    double rate;
    Date timeOfCreation;

    @ManyToOne 
    User rateAuthor;
    
    @ManyToOne
    Book ratedBook;
}
