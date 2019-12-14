package pl.bookworm.bookworm.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmailService {	
	JavaMailSender mailSender;
	
	public void sendConfirmationCode(String code, String mail) {
		log.info("Sending mail to " + mail);
		
		String messageBody = "Aby potwierdzić konto kliknij <a href='http://localhost:8189/confirm?code=" + code + "'> tutaj </a>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper  helper = new MimeMessageHelper(message);
		try {
			helper.setText(messageBody, true);
	    	helper.setTo(mail);
	    	helper.setSubject("Potwierdzenie konta");
	    	helper.setFrom("bookworm-zut@wp.pl");
		} catch (MessagingException e) {
			log.info("Error while creating confirmation code mail");
			e.printStackTrace();
		}    	    
			
    	mailSender.send(message);
    	log.info("Mail sent successfully");
	}
}
