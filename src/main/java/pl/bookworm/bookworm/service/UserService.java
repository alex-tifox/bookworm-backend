package pl.bookworm.bookworm.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {
	final private UserRepository userRepository;
	final private PasswordEncoder passwordEncoder;
	final private UserDetailsServiceImpl userDetailsService;
	ConfirmationCodeService confirmationCodeService;

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
    	newUser = userRepository.save(newUser);
    	
    	confirmationCodeService.sendConfirmationForUser(newUser);
    	
    	return true;
    }

    public User loginUser(User user) {
		if (checkUserPassword(user) && checkIfUserIsEnabled(user))
			return getLoggedInUser(user.getUsername());
		else {
			return User.builder()
					.username("")
					.build();
		}
	}

    public User getUser(String username)
    {
    	return userRepository.findByUsername(username);
    }
    
    private boolean checkUserPassword(User user) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

		log.info("Raw password: " + user.getPassword());
		log.info("Password in db: " + userDetails.getPassword());
		
		return passwordEncoder.matches(user.getPassword(), userDetails.getPassword());
	}

    private boolean checkIfUserIsEnabled(User user) {
		if (!userDetailsService.loadUserByUsername(user.getUsername()).isEnabled()) {
			log.info("User account is not confirmed");
			return false;
		}
		
		return true;
    }
    
	private User getLoggedInUser(String username) {
		return userRepository.findByUsername(username);
	}
}	