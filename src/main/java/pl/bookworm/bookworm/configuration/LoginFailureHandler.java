package pl.bookworm.bookworm.configuration;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 
    throws IOException, ServletException {

	  log.info("Authentication failure");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getOutputStream().println(objectMapper.writeValueAsString(generateErrorMessage(exception)));
  }
  
  private Map<String, Object> generateErrorMessage(AuthenticationException exception) {
	  Map<String, Object> data = new HashMap<>();
	  
	  data.put(
			  "timestamp", 
			  Calendar.getInstance().getTime());
	  data.put(
			  "exception", 
			  exception.getMessage());
	  
	  return data;
  }
}
