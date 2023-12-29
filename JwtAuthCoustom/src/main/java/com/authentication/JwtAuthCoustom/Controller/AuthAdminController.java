package com.authentication.JwtAuthCoustom.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthAdminController 
{
	@GetMapping("/admin")
	ResponseEntity<?> adminAcees()
	{
		return new ResponseEntity<>("Admin Aceess",HttpStatus.OK);
	}
	

}
