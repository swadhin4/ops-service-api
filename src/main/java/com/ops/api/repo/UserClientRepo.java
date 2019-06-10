package com.ops.api.repo;

import com.ops.api.data.model.LoginUser;
import com.ops.api.data.model.User;
import com.ops.api.exception.OpsDatabaseException;

public interface UserClientRepo {
	
	public User validateClientCredentials(String userName) throws OpsDatabaseException;

	public User validateUserDevice( String deviceId) throws OpsDatabaseException;
	
	public LoginUser getUserDetails(String username);
}
