package com.ops.api.service.impl;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.api.data.model.User;
import com.ops.api.exception.OpsDatabaseException;
import com.ops.api.repo.UserClientRepo;
import com.ops.api.service.AppAuthService;

@Service("appAuthService")
public class AppAuthServiceImpl implements AppAuthService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppAuthServiceImpl.class);

	@Autowired
	private UserClientRepo userClientRepo;
	
	@Override
	public User registerAppDevice( String deviceId) {
		
		return userClientRepo.validateUserDevice(deviceId);
	}

	@Override
	public User decodeUserDetails(String encodedUserDetails, String loginType, String deviceId) {
		// Decode data on other side, by processing encoded data
		try{
		byte[] valueDecoded = Base64.decodeBase64(encodedUserDetails);
		String decodedCredentials = new String(valueDecoded);
		String [] loginInfo=decodedCredentials.split(":");
		User userClientDetails = userClientRepo.validateClientCredentials(loginInfo[0]);
			if(!StringUtils.isEmpty(userClientDetails.getClientId())){
				userClientDetails.setUsername(loginInfo[0]);
				userClientDetails.setPassword(loginInfo[1]);
				return userClientDetails;
			}
		}catch(OpsDatabaseException e){
			e.printStackTrace();
		}
		return null;
	}

}
