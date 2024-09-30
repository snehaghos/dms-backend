package com.wish.dms_app_api.service.implement;

import com.wish.dms_app_api.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wish.dms_app_api.entity.User;
import com.wish.dms_app_api.repository.IUserRepository;
import com.wish.dms_app_api.security.JwtService;
import com.wish.dms_app_api.service.IAuthService;


import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserRepository userRepository;



    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired 
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserDetails userDetails;

    @Override
    public UserResponseDto registerUser(RegisterDto registerDto) {
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        User user = modelMapper.map(registerDto, User.class);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public LoginResponseDto checkLogin(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
            )
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginDto.getUsername());
            Optional<User> user = userRepository.findByUsername(loginDto.getUsername());

            String refreshToken = jwtService.generateRefreshToken(loginDto.getUsername());
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setToken(token);
            loginResponseDto.setRefreshToken(refreshToken);

            loginResponseDto.setData(modelMapper.map(user, UserResponseDto.class));
            return loginResponseDto;
        }
        
        throw new UsernameNotFoundException("User not found ");
    }

    @Override
    public LoginResponseDto refreshToken(RefreshTokenDto refreshTokenDto) {
        String username = jwtService.extractUsername(refreshTokenDto.getRefreshToken());
        User user = userRepository.findByUsername(username).orElseThrow();

        if(jwtService.isValidToken(refreshTokenDto.getRefreshToken(), user.getUsername())){
            String token = jwtService.generateToken(username);
            String refreshToken = jwtService.generateRefreshToken(user.getUsername());

            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setToken(token);
            loginResponseDto.setRefreshToken(refreshToken);
            loginResponseDto.setData(modelMapper.map(user, UserResponseDto.class));

            return loginResponseDto;
        }
           return null;
    }
}
