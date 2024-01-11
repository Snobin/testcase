package com.authentication.JwtAuthCoustom.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class userController {

	@GetMapping("/user")
	ResponseEntity<?> userAcees()
	{
		return new ResponseEntity<>("User Aceess",HttpStatus.OK);
	}
}
