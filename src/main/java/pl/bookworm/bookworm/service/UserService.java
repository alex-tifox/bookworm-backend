package pl.bookworm.bookworm.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.bookworm.bookworm.model.Book;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.BookRateRepository;
import pl.bookworm.bookworm.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {
	BookRateRepository bookRateRepository;
	final private UserRepository userRepository;
	final private PasswordEncoder passwordEncoder;
	ConfirmationCodeService confirmationCodeService;
	
	final public String USER_NOT_LOGGED_IN = "anonymousUser";
	
	public boolean registerUser(User newUser){
    	if (userRepository.findByUsername(newUser.getUsername()) != null) {
    		log.info("User is already existing");
			return false;
		}
    	
    	if (userRepository.findByEmail(newUser.getEmail()) != null){
    		log.info("Email is already in use");
    		return false;
    	}
    	
    	newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    	
    	newUser = User.builder()
    				.username(newUser.getUsername())
    				.password(newUser.getPassword())
    				.email(newUser.getEmail())
    				.userFullname(newUser.getUserFullname())
    				.userDescription(newUser.getUserDescription())
    				.enabled(false)
    				.build();
    	
    	newUser = userRepository.save(newUser);
    	confirmationCodeService.sendConfirmationForUser(newUser);
    	
    	return true;
    }
    
    public String readUsernameFromSecurity()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loggedInUsername = USER_NOT_LOGGED_IN;
		if(principal instanceof UserDetails)
			loggedInUsername = ((UserDetails)principal).getUsername();
		else
			loggedInUsername = principal.toString();
		
		return loggedInUsername;
	}

    public User getUser(String username)
    {
    	return userRepository.findByUsername(username);
    }
    
    public List<Book> topRatedBooks(String username) {
		User user = userRepository.findByUsername(username);
		
		if (user != null) {
			return bookRateRepository.findFirst5ByRateAuthorOrderByRateDesc(user);
		}
		
		return new Vector<Book>();
	}
    
    public List<Book> lastRatedBooks(String username) {
		User user = userRepository.findByUsername(username);
		
		if (user != null) {
			return bookRateRepository.findFirst5ByRateAuthorOrderByTimeOfCreationDesc(user);
		}
		
		return new Vector<Book>();
	}
}	