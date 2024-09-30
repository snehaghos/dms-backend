package com.wish.dms_app_api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Bean
	public UserDetails userDetails()
	{
		return new UserDetails() {
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return List.of();
			}

			@Override
			public String getPassword() {
				return "";
			}

			@Override
			public String getUsername() {
				return "";
			}
		};
	}


}
