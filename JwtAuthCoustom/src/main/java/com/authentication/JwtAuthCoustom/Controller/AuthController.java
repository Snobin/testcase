package com.authentication.JwtAuthCoustom.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.authentication.JwtAuthCoustom.DTO.CustomUserDetails;
import com.authentication.JwtAuthCoustom.DTO.JwtResponseDTO;
import com.authentication.JwtAuthCoustom.DTO.LoginDTO;
import com.authentication.JwtAuthCoustom.DTO.SignupDTO;
import com.authentication.JwtAuthCoustom.Entity.UserEntity;
import com.authentication.JwtAuthCoustom.JWT.JWTServices;
import com.authentication.JwtAuthCoustom.Repository.AuthRepository;
import com.authentication.JwtAuthCoustom.ServiceImp.AuthServiceImp;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
	@Autowired
	private JWTServices jwtUtil;

	@Autowired
	AuthServiceImp service;

	@Autowired
	private AuthServiceImp userDetailsService;
	@Autowired
	private AuthRepository authRepository;

	@PostMapping("/signup")
	ResponseEntity<?> registerUser(@Valid @RequestBody SignupDTO dto) {
		return new ResponseEntity<>(service.addUser(dto), HttpStatus.OK);
	}
	
	@PostMapping("/update")
	ResponseEntity<?> adminUpdate(@Valid @RequestBody SignupDTO dto) {
		return new ResponseEntity<>(service.updateAdmin(dto), HttpStatus.OK);
	}

	@PostMapping("/signupbyexcel")
	ResponseEntity<?> registerUserByExcel(@RequestParam("excelFile") MultipartFile excelFile) {
		return new ResponseEntity<>(service.processExcelData(excelFile), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<?> LoginandGenerate(@Valid @RequestBody LoginDTO dto) {

		if (service.checkemailpassword(dto)) {
			Optional<UserEntity> username = authRepository.findByEmail(dto.getEmail());
			UserEntity obj = username.get();
			final CustomUserDetails userDetails = service.loadUserByUsername(obj.getUsername());
			String token = jwtUtil.generateToken(userDetails);
			JwtResponseDTO jwt = new JwtResponseDTO();
			jwt.setToken(token);
			jwt.setUsername(userDetails.getUsername());
			jwt.setRole(userDetails.getRole());
			return new ResponseEntity<>(jwt, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Email or Password is Incorrect", HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/checktoken")
	public boolean validatetoke(@RequestBody JwtResponseDTO dtoo) {
		String username = jwtUtil.extractUsername(dtoo.getToken());
		CustomUserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		boolean result = jwtUtil.validateToken(dtoo.getToken(), userDetails);
		return result;

	}

	@PostMapping("/allclaims")
	ResponseEntity<?> getAllClaims(@RequestBody JwtResponseDTO dtoo) {
		Claims num = jwtUtil.extractAllClaims(dtoo.getToken());
		System.out.println(num);
		return new ResponseEntity(num, HttpStatus.OK);
	}

	@PostMapping("/extractclaims")
	ResponseEntity<?> extractClaims(@RequestBody JwtResponseDTO dtoo) {
		String num = jwtUtil.extractClaim(dtoo.getToken(), Claims::getSubject);
		System.out.println(num);
		return new ResponseEntity(num, HttpStatus.OK);
	}

	@PostMapping("/extractusername")
	ResponseEntity<?> extractusername(@RequestBody JwtResponseDTO dtoo) {
		String num = jwtUtil.extractUsername(dtoo.getToken());
		System.out.println(num);
		return new ResponseEntity(num, HttpStatus.OK);
	}

	@GetMapping("/current-user")
	public ResponseEntity<?> getCurrentUser(Principal principal) {
		if (principal != null && principal instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;

			// Assuming sub is the username in your case, modify this line accordingly
			CustomUserDetails usernameObj = (CustomUserDetails) authenticationToken.getPrincipal();

			System.err.println(usernameObj.toString());

			String username = principal.getName();

			return new ResponseEntity<>(this.userDetailsService.loadUserByUsername(username), HttpStatus.OK);
		} else {
			// Handle the case when there's no authentication information
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/upload")
	public ResponseEntity<?> uploadUsers(@RequestParam("excelFile") MultipartFile file) {
        List<UserEntity> users = new ArrayList<>();
		try {
			users = service.processExcelFile(file);
			// Save users to the database or perform other operations as needed
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error uploading users");
		}
	}

	@GetMapping("/userlist")
	public ResponseEntity<List<SignupDTO>> getAll() {
		List<SignupDTO> userList = service.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

}
