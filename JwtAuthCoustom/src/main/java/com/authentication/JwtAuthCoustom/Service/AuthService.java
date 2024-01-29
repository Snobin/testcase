package com.authentication.JwtAuthCoustom.Service;

import org.springframework.http.ResponseEntity;

import com.authentication.JwtAuthCoustom.DTO.LoginDTO;
import com.authentication.JwtAuthCoustom.DTO.SignupDTO;

public interface AuthService 
{
	public ResponseEntity<?> addUser(SignupDTO dto);
	public boolean checkemailpassword(LoginDTO ldto);
	public ResponseEntity updateAdmin(SignupDTO dto);

}

