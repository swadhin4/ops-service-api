package com.ops.api.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ops.api.data.model.Role;
import com.ops.api.data.model.User;
import com.ops.api.data.model.UserRole;
import com.ops.api.repo.UserClientRepo;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

	@Autowired
	private UserClientRepo userClientRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {

		log.debug("Authenticating {}", username);
		String lowercaseLogin = username.toLowerCase();
		User user=null;
		if (lowercaseLogin.contains("@")) {
			user = userClientRepo.validateClientCredentials(username);
			if(user!=null){
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				boolean accountNonLocked = true;
				List<UserRole> userRoles= new ArrayList<UserRole>();
				UserRole userRole=new UserRole();
				Role role = new Role();
				role.setRoleId(user.getRoleId());
				role.setDescription(user.getRoleDesc());
				userRole.setRole(role);
				userRoles.add(userRole);
				user.setUserRole(userRoles);
				AuthorizedUserDetails authorizedUserDetails = new AuthorizedUserDetails(user.getUsername(),
						user.getPassword(), true, accountNonExpired, credentialsNonExpired, accountNonLocked,
						getAuthorities(user.getUserRole()));
				authorizedUserDetails.setUser(user);
				return authorizedUserDetails;
				
			}else if (user == null) {
				throw new UsernameNotFoundException("User Credentials" + lowercaseLogin + " was not found in the database");
			}
		}
		return null; 
	}

	private Collection<? extends GrantedAuthority> getAuthorities(List<UserRole> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (UserRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole().getDescription()));
		}

		return authorities;
	}
	
}
