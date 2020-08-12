package com.pks.demo.service;

import java.sql.SQLException;

public interface CheckTokenService {
	public boolean compareToken(String token, String username) throws ClassNotFoundException, SQLException;

}
