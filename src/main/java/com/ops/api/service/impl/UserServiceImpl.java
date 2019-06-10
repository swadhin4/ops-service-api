package com.ops.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.api.data.model.LoginUser;
import com.ops.api.repo.UserClientRepo;
import com.ops.api.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserClientRepo userClientRepo;

	@Override
	public LoginUser getUserDetails(String username) {
		return userClientRepo.getUserDetails(username);
	}

}
