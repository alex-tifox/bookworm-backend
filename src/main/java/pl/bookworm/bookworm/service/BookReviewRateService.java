package pl.bookworm.bookworm.service;

import java.util.Optional;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.bookworm.bookworm.model.BookReview;
import pl.bookworm.bookworm.model.BookReviewRate;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.BookReviewRateRepository;
import pl.bookworm.bookworm.repository.BookReviewRepository;
import pl.bookworm.bookworm.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookReviewRateService 
{
	BookReviewRateRepository bookReviewRateRepository;
	BookReviewRepository bookReviewRepository;
	UserRepository userRepository;
	
	UserService userService;

	public Pair<String, HttpStatus> addNewRatingSecurity(BookReviewRate bookReviewRate)
	{
		int rating = bookReviewRate.getVote();
		BookReview bookReview = bookReviewRate.getBookReview();
		String username = userService.readUsernameFromSecurity();

		//Pair description: Pair.of(MESSAGE, HTTP_ERROR_CODE)
		Optional<BookReview> tempBookReview = bookReviewRepository.findById(bookReview.getId());
		if(!tempBookReview.isPresent())
			return Pair.of("Book review is not found", HttpStatus.CONFLICT);

		BookReview selectedBookReview = tempBookReview.get();
		if(username == userService.USER_NOT_LOGGED_IN)
			return Pair.of("User isn't currently logged in", HttpStatus.CONFLICT);
		
		User selectedUser = userRepository.findByUsername(username);
		if(selectedUser == null)
			return Pair.of("User doesn't exists", HttpStatus.CONFLICT);
		
		BookReviewRate isReviewRating = bookReviewRateRepository.findByBookReviewAndUser(selectedBookReview, selectedUser);
		if(isReviewRating != null)
			return Pair.of("User has already added a review rating to the database", HttpStatus.CONFLICT);
		
		bookReviewRate = BookReviewRate.builder()
										.bookReview(selectedBookReview)
										.user(selectedUser)
										.vote(rating)
										.build();
		
		if(rating == 1)
			bookReviewRate.getBookReview().setUpvotes(selectedBookReview.getUpvotes() + 1);
		else if(rating == -1)
			bookReviewRate.getBookReview().setDownvotes(selectedBookReview.getDownvotes() + 1);

		bookReviewRate = bookReviewRateRepository.save(bookReviewRate);
		
		return Pair.of("Added rating to the database", HttpStatus.OK);
	}
}
