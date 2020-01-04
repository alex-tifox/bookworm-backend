package pl.bookworm.bookworm.service;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResetPasswordService 
{
	PasswordEncoder passwordEncoder;
	UserRepository userRepository;
	EmailService emailService;
	
	public void changePasswordForUser(User user)
	{
		String password;
		String hash;
		
		do
		{
			password = generatePassword();
			hash = passwordEncoder.encode(password);
		} while(userRepository.findByPassword(hash) != null);
		
		user.setPassword(hash);
		user = userRepository.save(user);
		emailService.sendNewPassword(password, user.getEmail());
	}
	
	private String generatePassword()
	{
		PasswordGenerator passwdGen = new PasswordGenerator();
		CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
		lowerCaseRule.setNumberOfCharacters(2);
		
		CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
		upperCaseRule.setNumberOfCharacters(2);
		
		CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
		digitRule.setNumberOfCharacters(2);
		
		CharacterData specialChars = new CharacterData() 
		{
			@Override
			public String getErrorCode() 
			{
				return "An error occured during generating password";
			}
			
			@Override
			public String getCharacters() 
			{
				return "!@#$%&*()_+";
			}
		};
		
		CharacterRule specialCharsRule = new CharacterRule(specialChars);
		specialCharsRule.setNumberOfCharacters(2);

		return passwdGen.generatePassword(10, lowerCaseRule, upperCaseRule, digitRule);
	}	
}