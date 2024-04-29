package com.tg.cmd_diagnostics_service.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tg.cmd_diagnostics_service.configuration.JwtUtil;
import com.tg.cmd_diagnostics_service.exceptions.JwtTokenMissingException;
import com.tg.cmd_diagnostics_service.models.User;
import com.tg.cmd_diagnostics_service.services.impl.UserAuthService;

//Custom filter to handle JWT-based authentication
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserAuthService userAuthService;

	// Method to apply the filter to each incoming request
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Retrieves the "Authorization" header from the HTTP request
		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer")) {
			throw new JwtTokenMissingException("No JWT token found in the request headers");
		}
		
		// Extracts the JWT token by removing the "Bearer " prefix
		String token = header.substring(7);

		// Optional - verification
		jwtUtil.validateToken(token);

		User user = jwtUtil.getUser(token);

		UserDetails userDetails = userAuthService.loadUserByUsername(user.getUserName());
		
		// Creates an authentication token with the user details and authorities
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());

		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}

		filterChain.doFilter(request, response);
	}

}