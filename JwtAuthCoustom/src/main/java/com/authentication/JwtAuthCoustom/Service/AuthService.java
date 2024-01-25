package com.authentication.JwtAuthCoustom.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.authentication.JwtAuthCoustom.DTO.LoginDTO;
import com.authentication.JwtAuthCoustom.DTO.SignupDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface AuthService {
	
	public ResponseEntity<?> addUser(SignupDTO dto);

	public boolean checkemailpassword(LoginDTO ldto);

	public ObjectNode processExcelData(MultipartFile excelFile);

}
