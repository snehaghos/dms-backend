package com.wish.dms_app_api.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LoginResponseDto {
	private String token;
	private UserResponseDto data;
	private String refreshToken;
}
