package com.authentication.AuthService.JWT;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.authentication.AuthService.Entity.UserEntity;
import com.authentication.AuthService.Repository.AuthRepository;



@Configuration
public class ApplicationConfig {

	@Autowired
	AuthRepository repo;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				if (usernameExistsInYourSystem(username)) {
//
					return buildUserDetails(username);
				} else {
//		            
					throw new UsernameNotFoundException("Username not found: " + username);
				}
			}
		};

	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private boolean usernameExistsInYourSystem(String username) {
		Optional<UserEntity> opt = repo.findByEmail(username);
		if (opt != null) {
			return true;
		} else {
			return false;
		}

	}

	private UserDetails buildUserDetails(String username) {
		Optional<UserEntity> opt = repo.findByEmail(username);
		UserEntity details = opt.get();
		System.out.println((UserDetails) details);
		return (UserDetails) details;
	}

}
