package pl.bookworm.bookworm.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CorsFilter implements Filter {
		
		@Value(value = "${config.port.access.cors}")
		String allowOrigin;
	
	    @Override
	    public void init(FilterConfig filterConfig) throws ServletException {

	    }

	    @Override
	    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
	        HttpServletResponse response = (HttpServletResponse) servletResponse;
	        HttpServletRequest request= (HttpServletRequest) servletRequest;

	        response.setHeader("Access-Control-Allow-Origin", allowOrigin);
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        response.setHeader("Access-Control-Allow-Methods", "*");
	        response.setHeader("Access-Control-Allow-Headers",
	                "Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");
	        response.setHeader("Access-Control-Max-Age", "3600");

	        if (!request.getMethod().equals("OPTIONS")) {
	        	filterChain.doFilter(servletRequest, servletResponse);
	        }
	    }

	    @Override
	    public void destroy() {

	    }
}
