package com.pks.demo.dto;

public class AuthenticationResponse {
	
	private final String token;
	

	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}


	public String getJwt() {
		return token;
	}
	

}
