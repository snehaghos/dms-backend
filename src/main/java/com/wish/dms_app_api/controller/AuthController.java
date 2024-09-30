package com.wish.dms_app_api.controller;


import com.wish.dms_app_api.dto.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wish.dms_app_api.service.IAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private IAuthService authService;
	
	@Operation(summary = "Register new user" , description = "Register new user")
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterDto registerDto)
	{
		UserResponseDto registeredUser=authService.registerUser(registerDto);
		return new ResponseEntity<>(registeredUser,HttpStatus.OK);
	}
	 
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto)
	{
		LoginResponseDto loginResponseDto=authService.checkLogin(loginDto);
		return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);
	}


	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		return new ResponseEntity<>("Logout successful", HttpStatus.OK);
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
		LoginResponseDto loginResponseDto = authService.refreshToken(refreshTokenDto);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}

}

