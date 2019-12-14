package pl.bookworm.bookworm.service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.bookworm.bookworm.model.ConfirmationCode;
import pl.bookworm.bookworm.model.User;
import pl.bookworm.bookworm.repository.ConfirmationCodeRepository;
import pl.bookworm.bookworm.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConfirmationCodeService {
	ConfirmationCodeRepository confirmationCodeRepository;
	UserRepository userRepository;
	EmailService emailService;
	
	public void sendConfirmationForUser(User newUser) {
    	ConfirmationCode confirmationCode = ConfirmationCode.builder()
    			.user(newUser)
    			.code(generateUniqueCode())
    			.expirationDate(Date.from(new Date().toInstant().plus(14, ChronoUnit.DAYS)))
//    			.expirationDate(Date.from(new Date().toInstant().plus(1, ChronoUnit.MINUTES)))		// For testers
    			.build();
    	
    	confirmationCodeRepository.save(confirmationCode);
    	emailService.sendConfirmationCode(confirmationCode.getCode(), newUser.getEmail());
	}
	
	private String generateUniqueCode() {
		String code;
		
		do {
	    	code = UUID.randomUUID().toString(); 	
		} while(confirmationCodeRepository.findByCode(code) != null);
		
		return code;
	}
	
	public boolean UseConfirmationCode(String code) {	
		ConfirmationCode confirmationCode = confirmationCodeRepository.findByCode(code);

		if (!IsCodeValid(confirmationCode)) {
			return false;
		}
		
		ConfirmUser(confirmationCode.getUser());
		MarkCodeAsUsed(confirmationCode);
		
		return true;
	}
	
	private boolean IsCodeValid(ConfirmationCode confirmationCode) {
		if (confirmationCode == null) {
			log.info("Code not found in database");
			return false;
		}
				
		if (confirmationCode.getExpirationDate().before(new Date())) {
			log.info("Code in base, expired");
			return false;
		}
		
		if (confirmationCode.isUsed()) {
			log.info("Code is already used");
			return false;
		}
		
		return true;
	}
	
	private void ConfirmUser(User user) {	
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	private void MarkCodeAsUsed(ConfirmationCode confirmationCode) {
		confirmationCode.setUsed(true);
		confirmationCodeRepository.save(confirmationCode);
	}
}
