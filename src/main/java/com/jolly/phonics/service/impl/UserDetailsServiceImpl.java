package com.jolly.phonics.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jolly.phonics.dto.RegisterUserRequestDto;
import com.jolly.phonics.dto.RegisterUserResponseDto;
import com.jolly.phonics.entity.User;
import com.jolly.phonics.repository.UserRepository;
import com.jolly.phonics.service.UserDetailsService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto) {
		
		String password = registerUserRequestDto.getPassword();
    	byte[] base64DecodedPassword = Base64.getDecoder().decode(password);
    	String base64DecodedPasswordStr = new String(base64DecodedPassword, StandardCharsets.UTF_8);
		
		User user = new User();
		user.setUsername(registerUserRequestDto.getEmailId());
		user.setPassword(passwordEncoder.encode(base64DecodedPasswordStr));
		user.setEmail(registerUserRequestDto.getEmailId());
		user.setUserFullName(registerUserRequestDto.getFirstName()+" "+registerUserRequestDto.getLastName());
		return modelMapper.map(userRepository.save(user), RegisterUserResponseDto.class);
	}

}
