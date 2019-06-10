package com.ops.api.service;

import com.ops.api.data.model.LoginUser;

public interface UserService {

	public LoginUser getUserDetails(String username); 
}
