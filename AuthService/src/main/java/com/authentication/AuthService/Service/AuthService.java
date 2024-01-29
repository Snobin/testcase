package com.authentication.AuthService.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.authentication.AuthService.DTO.LoginDTO;
import com.authentication.AuthService.DTO.SignupDTO;
import com.authentication.AuthService.Entity.UserEntity;
import com.fasterxml.jackson.databind.node.ObjectNode;



public interface AuthService {
	
	public ResponseEntity<?> addUser(SignupDTO dto);

	public boolean checkemailpassword(LoginDTO ldto);

	public ObjectNode processExcelData(MultipartFile excelFile);
	public List<UserEntity> processExcelFile(MultipartFile file) throws IOException;

}
