package com.ops.api.service;

import com.ops.api.data.model.User;

public interface AppAuthService {

	public User  registerAppDevice( String deviceId);

	public User decodeUserDetails(String encodedUserDetails, String loginType, String deviceId);
}
