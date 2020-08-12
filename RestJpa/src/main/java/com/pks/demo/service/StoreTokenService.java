package com.pks.demo.service;

import java.sql.SQLException;

public interface StoreTokenService {
	public boolean saveToken(String token, String username) throws ClassNotFoundException, SQLException;
	
}
