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

		// Comment on your commit 5b8db4553ffdcd1f1be11708fa8a34689ae4ad04
		String messageBody = "Aby potwierdzić konto kliknij <a href='http://194.99.20.241:8189/confirm?code=" + code + "'> tutaj </a>";
		
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
	
	public void sendNewPassword(String password, String mail)
	{
		log.info("Sending mail with new password to " + mail);
		String messageBody = "Oto twoje nowe hasło do konta: <b>" + password +  "</b>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try
		{
			helper.setText(messageBody,true);
			helper.setTo(mail);
			helper.setSubject("Nowe hasło do konta");
			helper.setFrom("bookworm-zut@wp.pl");
		}
		catch (MessagingException e) 
		{
			log.info("Error while creating new password mail");
			e.printStackTrace();
		}
		
		mailSender.send(message);
		log.info("Mail with new password sent successfully");
	}
}
