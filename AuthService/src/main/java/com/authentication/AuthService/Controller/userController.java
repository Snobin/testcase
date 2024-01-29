package com.authentication.AuthService.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.authentication.AuthService.Entity.UserEntity;
import com.authentication.AuthService.Service.AuthService;

@RestController
@RequestMapping("/user")
public class userController {
	
	@Autowired
	AuthService authService;

	@GetMapping("/user")
	ResponseEntity<?> userAcees()
	{
		return new ResponseEntity<>("User Aceess",HttpStatus.OK);
	}
}
