package pl.bookworm.bookworm.model;

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
public class BookReviewRate 
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	Long Id;
	
	int vote; // -1 / 1
	
	@ManyToOne
	BookReview bookReview;
	
	@ManyToOne
	User user;
	
}
