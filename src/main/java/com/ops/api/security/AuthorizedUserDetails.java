package com.ops.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;



public class AuthorizedUserDetails extends User {

	private static final long serialVersionUID = -7720854002321418429L;

	private String salt;

	private com.ops.api.data.model.User user;

	public AuthorizedUserDetails(
			final String userName, 
			final String password, 
			final boolean enabled,
			final boolean accountNonExpired, final boolean credentialsNonExpired, final boolean accountNonLocked,
			final Collection<? extends GrantedAuthority> authorities) {
		super(userName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public String getSalt() {
		return salt;
	}

	public com.ops.api.data.model.User getUser() {
		return user;
	}

	public void setUser(com.ops.api.data.model.User user) {
		this.user = user;
	}





}
