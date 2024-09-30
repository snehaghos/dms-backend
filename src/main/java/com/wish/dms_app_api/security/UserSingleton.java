package com.wish.dms_app_api.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wish.dms_app_api.entity.User;

public class UserSingleton implements UserDetails {
	public User user;

	public UserSingleton(User user) {
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of();
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
	
		return user.getUsername();
	}
	
	public Long getId() {
	
		return user.getId();
	}
}
