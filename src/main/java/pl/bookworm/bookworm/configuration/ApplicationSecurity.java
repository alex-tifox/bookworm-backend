package pl.bookworm.bookworm.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class ApplicationSecurity  extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	public ApplicationSecurity(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
        	.addFilterBefore(corsFilter(), ChannelProcessingFilter.class)
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/admin/**")
					.hasRole("ADMIN")
				.antMatchers("/user/**")
					.hasRole("USER")
				.antMatchers("/**")
					.permitAll()
				.and()
			.addFilterAt(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.
			userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }
   
    @Bean
    JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() {
    	JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter();
    	
    	try {
			filter.setAuthenticationManager(this.authenticationManagerBean());
			filter.setAuthenticationSuccessHandler(loginSuccessHandler());       
			filter.setAuthenticationFailureHandler(loginFailureHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return filter;
    }
    
    @Bean
    LoginSuccessHandler loginSuccessHandler() {
    	return new LoginSuccessHandler();
    }
    
    @Bean
    LoginFailureHandler loginFailureHandler() {
    	return new LoginFailureHandler();
    }
}