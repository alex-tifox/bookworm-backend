package pl.bookworm.bookworm.configuration;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.bookworm.bookworm.model.User;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
				
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Method not supported " + request.getMethod());
		}
		
		User user = getUserFromRequest(request);
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

		setDetails(request, authRequest);		
		return authRequest;
	}
	
	private User getUserFromRequest(HttpServletRequest request) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			User user = mapper.readValue(request.getInputStream(), User.class);
			return user;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new User();
	}
}
