package com.jolly.phonics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jolly.phonics.dto.RegisterUserRequestDto;
import com.jolly.phonics.dto.RegisterUserResponseDto;
import com.jolly.phonics.entity.LoginRequest;
import com.jolly.phonics.service.UserDetailsService;

@RestController
public class UserController {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostMapping("/login")
	public ResponseEntity<?> doLogin(@RequestBody LoginRequest loginRequest){
		System.out.println(loginRequest.getUsername());
		return ResponseEntity.ok("Ok");
	}
	
	@PostMapping("/registerUser")
	public ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody RegisterUserRequestDto registerUserRequestDto){
		System.out.println(registerUserRequestDto);
		
		RegisterUserResponseDto response = userDetailsService.registerUser(registerUserRequestDto);
		
		return ResponseEntity.ok(response);
	}
}
