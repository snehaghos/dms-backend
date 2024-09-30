package com.wish.dms_app_api.dto;

import com.wish.dms_app_api.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
	
	
	private String name;
	
	
	private String username;

	private String email;
	
	private String password;
	 private Role role;
}
