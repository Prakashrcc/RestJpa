package com.pks.demo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pks.demo.dto.AuthenticationRequest;
import com.pks.demo.model.Authtoken;
import com.pks.demo.service.impl.AuthServiceImpl;
import com.pks.demo.service.impl.RestUserDetailsServiceImpl;


@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RestUserDetailsServiceImpl restUserDetailsServiceImpl;
	@Autowired
	private AuthServiceImpl authServiceImpl;

	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public Authtoken authenticate(@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws Exception {

		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);

		try {
			authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		} catch (Exception e) {
			throw new Exception("Incorrect userName or password", e);
		}

		UserDetails userDetails = restUserDetailsServiceImpl.loadUserByUsername(username);
		
		String token = authServiceImpl.generateToken(userDetails);
		Cookie cookie = new Cookie("Auth", token);
		response.addCookie(cookie);
		Authtoken authtoken = new Authtoken(token);
		return authtoken;
	}

	@RequestMapping(value="/signout", method=RequestMethod.GET)
	public String logout(HttpServletResponse response) {
		logger.info("logout method accessed");
		Cookie cookie = new Cookie("Auth", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");

		response.addCookie(cookie);
		logger.info("logout method completed");
		return "Log out Successfull";
	}

}
