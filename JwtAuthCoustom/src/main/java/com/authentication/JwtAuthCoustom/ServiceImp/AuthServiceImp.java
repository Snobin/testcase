package com.authentication.JwtAuthCoustom.ServiceImp;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authentication.JwtAuthCoustom.DTO.CustomUserDetails;
import com.authentication.JwtAuthCoustom.DTO.LoginDTO;
import com.authentication.JwtAuthCoustom.DTO.SignupDTO;
import com.authentication.JwtAuthCoustom.Entity.UserEntity;
import com.authentication.JwtAuthCoustom.Repository.AuthRepository;
import com.authentication.JwtAuthCoustom.Service.AuthService;

@Service
public class AuthServiceImp implements AuthService
{
	private static Logger logger = LogManager.getLogger(AuthServiceImp.class);

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	AuthRepository repo;
	
	public ResponseEntity<?> addUser(SignupDTO dto)
	{
		UserEntity entity = new UserEntity();
		try
		{
			entity.setEmail(dto.getEmail());
			entity.setName(dto.getName());
			String encodedPassword = passwordEncoder.encode(dto.getPassword());
			entity.setPassword(encodedPassword);
			entity.setPhoneNumber(dto.getPhoneNumber());
			entity.setRoles(dto.getRole());
			entity.setUsername(dto.getUsername());
			repo.save(entity);
			return new ResponseEntity<>("Successfully Inserted",HttpStatus.OK);
		}
		catch(Exception e)
		{
			logger.error("Error:" + e.getMessage(), e);
			return new ResponseEntity<>("Exception Occured",HttpStatus.OK);


		}

	}
	
	public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
	{
		try
		{
	        Optional<UserEntity> opt = repo.findIdByEmail(email);
	        if (opt.isEmpty()) {
	            throw new UsernameNotFoundException("User with email: " + email + " not found!");
	        } else {
	            UserEntity user = opt.get();
	            Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRoles()));
	            return new CustomUserDetails(
	            		user.getUsername(),
	                    user.getEmail(),
	                    user.getPassword(),
	                    authorities,
	                    user.getPhoneNumber(),
	                    user.getRoles()
	                    
	            );
	        }
			
		}
		catch(Exception e)
		{
			logger.error("Error:" + e.getMessage(), e);
            return new CustomUserDetails(null,null,null,null,null,null);
		}

    }
	
	public boolean checkemailpassword(LoginDTO ldto)
	{
		try
		{
			Optional<UserEntity> opt = repo.findIdByEmail(ldto.getEmail());
	        if (opt.isEmpty()) 
	        {
	        	return false;
	        }
	        else
	        {
	        	UserEntity user = opt.get();
	    		if(user.getEmail().equals(ldto.getEmail()) && passwordEncoder.matches(ldto.getPassword(), user.getPassword()))
	    		{
	    			return true;
	    		}
	    		return false;
			
	        }
		}
		catch(Exception e)
		{
			logger.error("Error:" + e.getMessage(), e);
			return false;
		}

	
	

    }
	

}
