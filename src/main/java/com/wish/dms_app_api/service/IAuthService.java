package com.wish.dms_app_api.service;

import com.wish.dms_app_api.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {

	UserResponseDto registerUser(RegisterDto registerDto);

	LoginResponseDto checkLogin(LoginDto loginDto);


	LoginResponseDto refreshToken(RefreshTokenDto refreshTokenDto);
}
