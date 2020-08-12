package com.pks.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pks.demo.model.RestUser;
import com.pks.demo.repository.RestUserRepository;
import com.pks.demo.service.RestUserService;

@Service
public class RestUserServiceImpl implements RestUserService {
	@Autowired
	private RestUserRepository restUserRepository;
	Logger logger = LoggerFactory.getLogger(RestUserServiceImpl.class);

	@Override
	public RestUser getUser(String username) {
		logger.info("getUser() method started for  username: " + username);
		RestUser restUser = null;
		try {
			restUser = restUserRepository.findById(username).orElse(null);
		} catch (Exception e) {
			logger.warn(e + " Exception occured for username : " + username);
		}

		if (restUser == null) {
			logger.warn("No User found with username: " + username);
		}
		logger.info("getUser() method completed for  username: " + username);

		return restUser;
	}

}
