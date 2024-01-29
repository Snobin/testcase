package com.authentication.AuthService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.AuthService.DTO.CustomUserDetails;
import com.authentication.AuthService.ServiceImp.AuthServiceImp;

@RestController
@RequestMapping("/api")
public class AuthAdminController 
{

    @Autowired
    private AuthServiceImp userDetailsService;
	
	@GetMapping("/admin")
	ResponseEntity<?> adminAcees()
	{
		return new ResponseEntity<>("Admin Aceess",HttpStatus.OK);
	}
	


	

}
