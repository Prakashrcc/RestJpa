package com.pks.demo.service;

import java.sql.SQLException;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
	public String generateToken(UserDetails userDetails) throws Exception;

	public boolean validateToken(String token) throws ClassNotFoundException, SQLException;

	public String extractUsername(String token);

	public boolean datevalidator(String date);
}
