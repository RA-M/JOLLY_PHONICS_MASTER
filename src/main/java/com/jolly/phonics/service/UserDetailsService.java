package com.jolly.phonics.service;

import com.jolly.phonics.dto.RegisterUserRequestDto;
import com.jolly.phonics.dto.RegisterUserResponseDto;
import com.jolly.phonics.entity.User;

public interface UserDetailsService {
	
	User findByUsername(String username);
	RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto);
}
