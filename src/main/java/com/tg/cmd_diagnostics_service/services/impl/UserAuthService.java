package com.tg.cmd_diagnostics_service.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tg.cmd_diagnostics_service.models.Role;
import com.tg.cmd_diagnostics_service.models.User;

//Implementation of UserDetailsService to load user details for Spring Security
@Service
public class UserAuthService implements UserDetailsService {
		
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Retrieves the user by username using the UserService
		User user = userService.getUserByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found.");
		}
		// Gets the roles associated with the user
		List<Role> roles = userService.getRoles(username);
		List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
			return new SimpleGrantedAuthority(r.getRoleName());
		}).collect(Collectors.toList());
		
		// Returns a UserDetails object for Spring Security, containing the username, password, and authorities
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				grantedAuthorities);
	}

	public User getUserByUsername(String username) {
		User user= userService.getUserByName(username);

		if (user != null) {

			return user;
		}

		return null;
	}

	
}