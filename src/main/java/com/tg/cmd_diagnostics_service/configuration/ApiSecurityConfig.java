package com.tg.cmd_diagnostics_service.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tg.cmd_diagnostics_service.filter.JwtAuthenticationFilter;
import com.tg.cmd_diagnostics_service.services.impl.UserAuthService;

@Configuration
//@EnableWebSecurity
@EnableMethodSecurity // Enables method-level security annotations like @PreAuthorize
public class ApiSecurityConfig {

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private ApiAuthenticationEntryPoint authenticationEntryPoint;

	// Configures the authentication manager to use the user authentication service
	// and a password encoder
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Specifies that the user authentication service provides user details and uses
		// BCrypt for password encoding
		auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
	}

	// Defines a customizer to ignore specific endpoints for authentication
	@Bean
	public WebSecurityCustomizer ignoringCustomizer() {

		// Allows unauthenticated access to these endpoints
		return (web) -> web.ignoring().requestMatchers("/signin", "/signup");
	}

	// Configures the HTTP security, defining rules for request authorization,
	// exception handling, and session management
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authz -> authz.requestMatchers("/signin", "/signup")

				.permitAll().anyRequest().authenticated())

				.exceptionHandling((exception) -> exception.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	// Prevents double registration of the JWT authentication filter by disabling its registration
	@Bean
	public RegistrationBean jwtAuthFilterRegister(JwtAuthenticationFilter filter) {
		FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}
	
	// Provides the authentication manager, required for managing authentication
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/*
	 * @Bean public AuthenticationManager authenticationManagerBean() throws
	 * Exception { return super.authenticationManagerBean(); }
	 */
	
	// Provides a password encoder for encoding user passwords
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}