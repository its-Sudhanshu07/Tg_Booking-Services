package com.tg.cmd_diagnostics_service.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tg.cmd_diagnostics_service.configuration.JwtUtil;
import com.tg.cmd_diagnostics_service.dtos.JwtRequest;
import com.tg.cmd_diagnostics_service.dtos.JwtResponse;
import com.tg.cmd_diagnostics_service.exceptions.DisabledUserException;
import com.tg.cmd_diagnostics_service.exceptions.InvalidUserCredentialsException;
import com.tg.cmd_diagnostics_service.models.Role;
import com.tg.cmd_diagnostics_service.models.User;
import com.tg.cmd_diagnostics_service.services.impl.UserAuthService;
import com.tg.cmd_diagnostics_service.services.impl.UserService;


@RestController
public class JwtRestController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping(value="/signin",produces = {"application/json"})
	// Endpoint to sign in and generate a JWT token
	public ResponseEntity<JwtResponse> generateJwtToken(@RequestBody JwtRequest jwtRequest) {
		
		// Generates a JWT token after validating user credentials
		System.out.println(jwtRequest.getUserName()+""+jwtRequest.getUserPwd());
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getUserPwd()));
			
		// Attempts to authenticate the user with the provided username and password
		} catch (DisabledException e) {
			throw new DisabledUserException("User Inactive");
		} catch (BadCredentialsException e) {
			throw new InvalidUserCredentialsException("Invalid Credentials");
		}
		// Loads the user details from the user authentication service
		UserDetails userDetails = userAuthService.loadUserByUsername(jwtRequest.getUserName());
		String username = userDetails.getUsername();
		String userpwd = userDetails.getPassword();
		// Gets the roles from user details and converts them to a list of role names
		List<String> roles = userDetails.getAuthorities().stream().map(r -> r.getAuthority())
				.collect(Collectors.toList());
		
		
		User user = new User();
		user.setUserName(username);
		user.setPassword(userpwd);
		List<Role> roleList = new ArrayList(roles);
		user.setRoles(roleList);
		// Generates a JWT token for the authenticated user
		String token = jwtUtil.generateToken(user);
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}
	
	 // Endpoint to sign up and register a new user
	@PostMapping(value="/signup",produces = {"application/json"})
	public ResponseEntity<String> signup(@RequestBody User user) {
		
		System.out.print(user.getUserName());
		System.out.print(user.getPassword());
		System.out.print(user.getRoles());
		User userObj = userAuthService.getUserByUsername(user.getUserName());

		if (userObj == null) {
			// If the user doesn't exist, save the new user
			userService.saveUser(user);
			return new ResponseEntity<String>("User successfully registered", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User already exists", HttpStatus.CONFLICT);
		}
	}

}