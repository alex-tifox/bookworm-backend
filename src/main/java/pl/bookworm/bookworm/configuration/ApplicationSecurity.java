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

@Configuration
@EnableWebSecurity
public class ApplicationSecurity  extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	public ApplicationSecurity(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http
//			.authorizeRequests()
//				.antMatchers("/admin/**")
//					.hasRole("ADMIN")
//				.antMatchers("/user/**")
//					.hasRole("USER")
//				.antMatchers("/**").
//					permitAll()
//				.and()
//			.formLogin()
//				.loginPage("/login")
//				.defaultSuccessUrl("/")
//				.permitAll()
//				.and()
//			.logout()
//				.logoutSuccessUrl("/login");
		http.cors().and().csrf().disable();

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

	/*
	void EncodeExample(String password) {
		String encoded = passwordEncoder().encode(password);
	}
	*/

	/*
	@PreAuthorize("hasRole('ADMIN')")
	void OnlyAdminCanCallExample() {

	}
	*/
}