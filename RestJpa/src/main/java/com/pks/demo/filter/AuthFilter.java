package com.pks.demo.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pks.demo.controller.ProductController;
import com.pks.demo.service.impl.AuthServiceImpl;
import com.pks.demo.service.impl.RestUserDetailsServiceImpl;



@Component
public class AuthFilter extends OncePerRequestFilter {
	@Autowired
	private RestUserDetailsServiceImpl restUserDetailsServiceImpl;
	@Autowired
	private AuthServiceImpl authServiceImpl;
	Logger logger = LoggerFactory.getLogger(AuthFilter.class);
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Auth");
		
		if(authHeader==null) {
		Cookie[] cookies=request.getCookies();
		if (cookies != null) {
			 for (Cookie cookie : cookies) {
			   if (cookie.getName().equals("Auth")) {
				   authHeader=cookie.getValue();
				   break;
			    }
			  }
			}
		}
		
		
		String username = null;

		
		if (authHeader != null) {
			username = authServiceImpl.extractUsername(authHeader);
		}
		if (username != null) {
			UserDetails userDetails = restUserDetailsServiceImpl.loadUserByUsername(username);
			try {
				if (authServiceImpl.validateToken(authHeader)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			} catch (ClassNotFoundException e) {
				logger.error(e+" Exception occured for username: "+username);
				
			} catch (SQLException e) {
				logger.error(e+" Exception occured for username: "+username);
				
			}

		}
		filterChain.doFilter(request, response);

	}

}
