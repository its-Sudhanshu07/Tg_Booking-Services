package com.tg.cmd_diagnostics_service.configuration;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tg.cmd_diagnostics_service.exceptions.JwtTokenMalformedException;
import com.tg.cmd_diagnostics_service.exceptions.JwtTokenMissingException;
import com.tg.cmd_diagnostics_service.models.Role;
import com.tg.cmd_diagnostics_service.models.User;
 

@Component
public class JwtUtil {
	
	// Injects the JWT secret from application properties
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.token.validity}")
	private long tokenValidity;

	public User getUser(final String token) {
		try {
			// Parses the JWT and retrieves its claims
			Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			User user = new User();
			user.setUserName(body.getSubject());
			// Extracts roles from the JWT and converts them into a list of Role objects
			List<Role> roles = Arrays.asList(body.get("roles").toString().split(",")).stream().map(r -> new Role(r))
					.collect(Collectors.toList());
			user.setRoles(roles);
			return user;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}
	
	// Generates a JWT with user information and expiration
	public String generateToken(User user) {
		
		Claims claims = Jwts.claims().setSubject(user.getUserName());
		// Adds roles to the claims
		claims.put("roles", user.getRoles());
		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + tokenValidity;
		Date exp = new Date(expMillis);
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact(); // Compacts the JWT to a string
	}
	
	// Validates the JWT for proper signature and structure
	public void validateToken(final String token) {
		try {
			System.out.println(token);
			
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		} catch (SignatureException ex) {
			throw new JwtTokenMalformedException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new JwtTokenMalformedException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new JwtTokenMalformedException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenMalformedException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenMissingException("JWT claims string is empty.");
		}
	}

}