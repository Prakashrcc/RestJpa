package com.pks.demo.util;


import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pks.demo.model.RestUser;
import com.pks.demo.repository.RestUserRepository;
@Service
public class StoreToken {
	@Autowired
	private RestUserRepository restUserRepository;
	Logger logger = LoggerFactory.getLogger(StoreToken.class);

	public boolean saveToken(String token, String username) throws ClassNotFoundException, SQLException {
		logger.info("saveToken() method accessed in StoreToken class with username: " + username);

		RestUser restUser = null;
		try {
			restUser = restUserRepository.findById(username).orElse(null);
			if (restUser != null) {
				restUser.setToken(token);
				try {
					restUserRepository.save(restUser);
				} catch (Exception e) {
					logger.warn(e + " Exception occured while saving token for username: " + username);
				}
			}

		} catch (Exception e) {
			logger.warn("exception occured while saving Token in StoreToken class with username: " + username);
			return false;
		}
		logger.info("saveToken() method completed in StoreToken class with username: " + username);
		return true;

	}

}
