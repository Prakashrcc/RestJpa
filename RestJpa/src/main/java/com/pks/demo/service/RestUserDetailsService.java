package com.pks.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pks.demo.exception.ProductNotFoundException;
import com.pks.demo.model.RestUser;

@Service
public class RestUserDetailsService implements UserDetailsService {
	@Autowired
	private RestUserService restUserService;
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RestUser restUser = restUserService.getUser(username);
		if (restUser == null) {
			throw new ProductNotFoundException("No user found with the username: " + username);
		}

		return new User(restUser.getUsername(), restUser.getPassword(), new ArrayList<>());
	}

}
