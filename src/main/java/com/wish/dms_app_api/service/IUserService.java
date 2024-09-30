package com.wish.dms_app_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wish.dms_app_api.dto.RegisterDto;
import com.wish.dms_app_api.dto.UserResponseDto;

@Service
public interface IUserService {
	List<UserResponseDto>getAllUser();
	UserResponseDto getUserById(Long id);
	UserResponseDto createUser(RegisterDto registerDto);

}
