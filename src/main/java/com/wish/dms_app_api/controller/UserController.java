package com.wish.dms_app_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wish.dms_app_api.dto.RegisterDto;
import com.wish.dms_app_api.dto.UserResponseDto;
import com.wish.dms_app_api.service.IAuthService;
import com.wish.dms_app_api.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> index(){

        List<UserResponseDto> users= userService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}

