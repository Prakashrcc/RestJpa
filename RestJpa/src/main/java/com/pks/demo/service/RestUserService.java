package com.pks.demo.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pks.demo.model.RestUser;
import com.pks.demo.repository.RestUserRepository;
@Service
public class RestUserService {
	@Autowired
	private RestUserRepository restUserRepository;
	Logger logger = LoggerFactory.getLogger(RestUserService.class);

	public RestUser getUser(String username) {
		logger.info("getUser() method started for  username: " + username);
		RestUser restUser = null;
		try {
		restUser = restUserRepository.findById(username).orElse(null);
		}
		catch (Exception e) {
			logger.warn(e+ " Exception occured for username : "+username);
		}
		
		if (restUser == null) {
			logger.warn("No User found with username: " + username);
		}
		logger.info("getUser() method completed for  username: " + username);

		return restUser;
	}

}
