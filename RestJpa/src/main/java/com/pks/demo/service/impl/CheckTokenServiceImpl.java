package com.pks.demo.service.impl;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pks.demo.model.RestUser;
import com.pks.demo.repository.RestUserRepository;
import com.pks.demo.service.CheckTokenService;

@Service
public class CheckTokenServiceImpl implements CheckTokenService {
	@Autowired
	private RestUserRepository restUserRepository;
	Logger logger = LoggerFactory.getLogger(CheckTokenServiceImpl.class);

	@Override
	public boolean compareToken(String token, String username) throws ClassNotFoundException, SQLException {

		logger.info("compareToken() method accessed in CheckToken class with username: " + username);

		RestUser restUser = null;
		try {
			restUser = restUserRepository.findById(username).orElse(null);
		} catch (Exception e) {
			logger.warn("exception occured while checking Token in CheckToken class with username: " + username);
			return false;
		}
		if (restUser != null) {

			if (restUser.getToken().equals(token)) {
				logger.info("compareToken() method completed in CheckToken class with username: " + username);
				return true;
			}
		}

		logger.info("compareToken() method completed in CheckToken class with username: " + username);
		return false;
	}

}
