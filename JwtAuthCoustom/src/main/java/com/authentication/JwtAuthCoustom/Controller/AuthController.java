package com.authentication.JwtAuthCoustom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.JwtAuthCoustom.DTO.CustomUserDetails;
import com.authentication.JwtAuthCoustom.DTO.JwtResponseDTO;
import com.authentication.JwtAuthCoustom.DTO.LoginDTO;
import com.authentication.JwtAuthCoustom.DTO.SignupDTO;
import com.authentication.JwtAuthCoustom.JWT.JWTServices;
import com.authentication.JwtAuthCoustom.ServiceImp.AuthServiceImp;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/auth")
public class AuthController 
{
    @Autowired
    private JWTServices jwtUtil;
    
	@Autowired
	AuthServiceImp service;
	
    @Autowired
    private AuthServiceImp userDetailsService;
    
    
	@PostMapping("/signup")
	ResponseEntity<?> registerUser(@RequestBody SignupDTO dto)
	{
		return new ResponseEntity<>(service.addUser(dto),HttpStatus.OK);
	}
	
    @PostMapping("/login")
    public ResponseEntity<?> LoginandGenerate(@RequestBody LoginDTO dto)
    {
    	if(service.checkemailpassword(dto))
    	{
            final CustomUserDetails userDetails = service.loadUserByUsername(dto.getEmail());
            String token = jwtUtil.generateToken(userDetails);
            JwtResponseDTO jwt=new JwtResponseDTO();
            jwt.setToken(token);
            jwt.setUsername(userDetails.getUsername());
            jwt.setRole(userDetails.getRole());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
    	}
    	else
    	{
    		return new ResponseEntity<>("Email or Password is Incorrect", HttpStatus.BAD_REQUEST);
    	}

    }
    
    @PostMapping("/checktoken")
    public boolean validatetoke(@RequestBody JwtResponseDTO dtoo)
    {
        String username = jwtUtil.extractUsername(dtoo.getToken());
        CustomUserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        boolean result=jwtUtil.validateToken(dtoo.getToken(), userDetails);
        return result;

    	
    }
    
    @PostMapping("/allclaims")
    ResponseEntity<?> getAllClaims(@RequestBody JwtResponseDTO dtoo)
    {
    	Claims num=jwtUtil.extractAllClaims(dtoo.getToken());
    	System.out.println(num);
    	return new ResponseEntity(num,HttpStatus.OK);
    }
    
    @PostMapping("/extractclaims")
    ResponseEntity<?> extractClaims(@RequestBody JwtResponseDTO dtoo)
    {
    	String num=jwtUtil.extractClaim(dtoo.getToken(), Claims::getSubject);
    	System.out.println(num);
    	return new ResponseEntity(num,HttpStatus.OK);
    }
    
    @PostMapping("/extractusername")
    ResponseEntity<?> extractusername(@RequestBody JwtResponseDTO dtoo)
    {
    	String num=jwtUtil.extractUsername(dtoo.getToken());
    	System.out.println(num);
    	return new ResponseEntity(num,HttpStatus.OK);
    }

    
}
