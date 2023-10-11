package com.jolly.phonics.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jolly.phonics.entity.AuthRequest;
import com.jolly.phonics.entity.AuthResponse;
import com.jolly.phonics.entity.User;
import com.jolly.phonics.service.UserDetailsService;
import com.jolly.phonics.util.JwtUtil;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/")
    public String welcome() {
        return "Welcome to Jolly Phonics !!";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody @Valid AuthRequest authRequest) throws Exception {
        /*try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }*/
    	
    	authenticate(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(authRequest.getUsername())));
    }
    private void authenticate(String username, String password) throws Exception{
    	User user = userDetailsService.findByUsername(username);
    	if(user == null) {
    		throw new Exception("Invalid username of password");
    	}
    	
    	String dbPassword = user.getPassword();
    	byte[] base64DecodedPassword = Base64.getDecoder().decode(password);
    	String base64DecodedPasswordStr = new String(base64DecodedPassword, StandardCharsets.UTF_8);
    	if(password == null || "".equals(password) || !passwordEncoder.matches(base64DecodedPasswordStr, dbPassword)) {
    		throw new Exception("Invalid username of password");
    	}
    	try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, base64DecodedPasswordStr)
            );
        } catch (Exception ex) {
        	throw new Exception("Invalid username of password");
        }
    }
}
