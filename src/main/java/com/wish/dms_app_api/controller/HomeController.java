package com.wish.dms_app_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api")
public class HomeController {

	@GetMapping
	public ResponseEntity<String> welcome(HttpServletRequest request)
	{
		return new ResponseEntity<>("Welcome yeeee",HttpStatus.OK);
	}
	
	@GetMapping("/home")
	public ResponseEntity<String> home()
	{
		return new ResponseEntity<>("Welcome yeeee",HttpStatus.OK);
	}
}
