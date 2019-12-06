package pl.bookworm.bookworm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
    public void registerUser(User newUser){
    	
    	if (userRepository.findByUsername(newUser.getUsername()) != null)
    		return;
    	
    	newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    	userRepository.save(newUser);
    }
}	