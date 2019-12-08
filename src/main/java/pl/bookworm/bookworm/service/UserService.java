package pl.bookworm.bookworm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.UserRepository;

import java.util.logging.Logger;

@Service
public class UserService {

	final private Logger logger = Logger.getLogger(UserService.class.getName());

	final private UserRepository userRepository;
	final private PasswordEncoder passwordEncoder;
	final private UserDetailsServiceImpl userDetailsService;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
	}

	public boolean registerUser(User newUser){
    	if (userRepository.findByUsername(newUser.getUsername()) != null) {
    		logger.info("User is already existing");
			return false;
		}
    	newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    	userRepository.save(newUser);
    	return true;
    }

    public User loginUser(User user) {
		if (checkUserPassword(user))
			return getLoggedInUser(user.getUsername());
		else
			return new User("");
	}

    private boolean checkUserPassword(User user) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

		logger.info("Raw password: " + user.getPassword());
		logger.info("Password in db: " + userDetails.getPassword());

		return passwordEncoder.matches(user.getPassword(), userDetails.getPassword());
	}

	private User getLoggedInUser(String username) {
		return userRepository.findByUsername(username);
	}
}	