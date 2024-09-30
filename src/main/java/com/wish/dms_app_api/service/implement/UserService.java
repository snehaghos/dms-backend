package com.wish.dms_app_api.service.implement;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wish.dms_app_api.dto.DocumentResponseDto;
import com.wish.dms_app_api.dto.RegisterDto;
import com.wish.dms_app_api.dto.UserResponseDto;
import com.wish.dms_app_api.entity.User;
import com.wish.dms_app_api.repository.IUserRepository;
import com.wish.dms_app_api.service.IUserService;

@Service
public class UserService implements IUserService{
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Override
    public List<UserResponseDto> getAllUser() {
        List<User> users=  userRepository.findAll();

        return users.stream().map(user->mapper.map(user,UserResponseDto.class))
                .collect(Collectors.toList());
    }

	

	@Override
	public UserResponseDto createUser(RegisterDto registerDto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public UserResponseDto getUserById(Long id) {
	    User user = userRepository.findById(id)
	                   .orElseThrow(() -> new RuntimeException("User not found"));
	    
	    // Map the user to UserResponseDto
	    UserResponseDto userResponseDto = mapper.map(user, UserResponseDto.class);

	    // Optionally, include documents in the response
//	    userResponseDto.setDocuments(user.getDocuments().stream()
//	        .map(doc -> mapper.map(doc, DocumentResponseDto.class))
//	        .collect(Collectors.toList()));

	    return userResponseDto;
	}



}
