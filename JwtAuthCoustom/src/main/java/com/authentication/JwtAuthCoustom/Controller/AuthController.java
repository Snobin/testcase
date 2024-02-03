package com.authentication.JwtAuthCoustom.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private JWTServices jwtUtil;

	@Autowired
	private AuthServiceImp authService;

	@Autowired
	private AuthRepository authRepository;

	@PostMapping("/signup")
	ResponseEntity<?> registerUser(@Valid @RequestBody SignupDTO dto) {
		try {
			return new ResponseEntity<>(authService.addUser(dto), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error registering user: {}", e.getMessage(), e);
			return new ResponseEntity<>("Error registering user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/update")
	ResponseEntity<?> adminUpdate(@Valid @RequestBody SignupDTO dto) {
		try {
			return new ResponseEntity<>(authService.updateAdmin(dto), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error updating admin: {}", e.getMessage(), e);
			return new ResponseEntity<>("Error updating admin", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/signupbyexcel")
	ResponseEntity<?> registerUserByExcel(@RequestParam("excelFile") MultipartFile excelFile) {
		try {
			return new ResponseEntity<>(authService.processExcelData(excelFile), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error registering user by Excel: {}", e.getMessage(), e);
			return new ResponseEntity<>("Error registering user by Excel", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> LoginandGenerate(@Valid @RequestBody LoginDTO dto) {
		try {
			if (authService.checkemailpassword(dto)) {
				Optional<UserEntity> username = authRepository.findByEmail(dto.getEmail());
				UserEntity obj = username.get();
				final CustomUserDetails userDetails = authService.loadUserByUsername(obj.getUsername());
				String token = jwtUtil.generateToken(userDetails);
				JwtResponseDTO jwt = new JwtResponseDTO();
				jwt.setToken(token);
				jwt.setUsername(userDetails.getUsername());
				jwt.setRole(userDetails.getRole());
				return new ResponseEntity<>(jwt, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Email or Password is Incorrect", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Error during login: {}", e.getMessage());
			return new ResponseEntity<>("Error during login", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/checktoken")
	public boolean validatetoke(@RequestBody JwtResponseDTO dtoo) {
		try {
			String username = jwtUtil.extractUsername(dtoo.getToken());
			CustomUserDetails userDetails = this.authService.loadUserByUsername(username);
			return jwtUtil.validateToken(dtoo.getToken(), userDetails);
		} catch (Exception e) {
			logger.error("Error validating token: {}", e.getMessage(), e);
			return false;
		}
	}

	@PostMapping("/allclaims")
	ResponseEntity<?> getAllClaims(@RequestBody JwtResponseDTO dtoo) {
		try {
			Claims num = jwtUtil.extractAllClaims(dtoo.getToken());
			return new ResponseEntity<>(num, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error extracting all claims: {}", e.getMessage(), e);
			return new ResponseEntity<>("Error extracting all claims", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/extractclaims")
	ResponseEntity<?> extractClaims(@RequestBody JwtResponseDTO dtoo) {
		try {
			String num = jwtUtil.extractClaim(dtoo.getToken(), Claims::getSubject);
			return new ResponseEntity(num, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error extracting claims: {}", e.getMessage(), e);
			return new ResponseEntity<>("Error extracting claims", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/extractusername")
	ResponseEntity<?> extractusername(@RequestBody JwtResponseDTO dtoo) {
		try {
			String num = jwtUtil.extractUsername(dtoo.getToken());
			return new ResponseEntity(num, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error extracting username: {}", e.getMessage(), e);
			return new ResponseEntity<>("Error extracting username", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/current-user")
	public ResponseEntity<?> getCurrentUser(Principal principal) {
		try {
			if (principal != null && principal instanceof UsernamePasswordAuthenticationToken) {
				UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
				CustomUserDetails usernameObj = (CustomUserDetails) authenticationToken.getPrincipal();
				String username = principal.getName();
				return new ResponseEntity<>(this.authService.loadUserByUsername(username), HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			logger.error("Error getting current user: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/upload")
	public ResponseEntity<?> uploadUsers(@RequestParam("excelFile") MultipartFile file) {

		try {
			List<UserEntity> users = authService.processExcelFile(file);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error uploading users: {}", e.getMessage(), e);
			return ResponseEntity.status(500).body("Error uploading users");
		}
	}

	@GetMapping("/userlist")
	public ResponseEntity<List<SignupDTO>> getAll() {
		try {
			List<SignupDTO> userList = authService.getAllUsers();
			return new ResponseEntity<>(userList, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error getting user list: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
