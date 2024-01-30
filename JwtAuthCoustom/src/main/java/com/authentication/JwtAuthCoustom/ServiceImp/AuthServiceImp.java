package com.authentication.JwtAuthCoustom.ServiceImp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Iterator;

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
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.IOException;

import java.text.SimpleDateFormat;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.authentication.JwtAuthCoustom.DTO.CustomUserDetails;
import com.authentication.JwtAuthCoustom.DTO.LoginDTO;
import com.authentication.JwtAuthCoustom.DTO.SignupDTO;
import com.authentication.JwtAuthCoustom.Entity.UserEntity;
import com.authentication.JwtAuthCoustom.Repository.AuthRepository;
import com.authentication.JwtAuthCoustom.Service.AuthService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class AuthServiceImp implements AuthService {
	private static Logger logger = LogManager.getLogger(AuthServiceImp.class);

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private AuthRepository repo;

	public ResponseEntity<?> addUser(SignupDTO dto) {
		String role = dto.getRole() == "false" ? "USER" : "ADMIN";
		UserEntity entity = new UserEntity();
		try {
			entity.setEmail(dto.getEmail());
			entity.setFirstName(dto.getFirstName());
			entity.setLastName(dto.getLastName());
			String encodedPassword = passwordEncoder.encode(dto.getPassword());
			entity.setPassword(encodedPassword);
			entity.setPhoneNumber(dto.getPhoneNumber());
			entity.setRoles(role);
			entity.setUsername(dto.getUsername());
			repo.save(entity);
			return new ResponseEntity<>("Successfully Inserted", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ResponseEntity<>("Exception Occured", HttpStatus.OK);

		}

	}

	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			Optional<UserEntity> opt = repo.findByUsername(username);

			if (opt.isEmpty()) {
				throw new UsernameNotFoundException("User with username: " + username + " not found!");
			} else {
				UserEntity user = opt.get();
				Set<SimpleGrantedAuthority> authorities = Collections
						.singleton(new SimpleGrantedAuthority(user.getRoles()));
				return new CustomUserDetails(user.getUsername(), user.getEmail(), user.getPassword(), authorities,
						user.getPhoneNumber(), user.getRoles());
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new CustomUserDetails(null, null, null, null, null, null);
		}

	}

	public boolean checkemailpassword(LoginDTO ldto) {
		try {
			Optional<UserEntity> opt = repo.findByEmail(ldto.getEmail());
			if (opt.isEmpty()) {
				return false;
			} else {
				UserEntity user = opt.get();
				if (user.getEmail().equals(ldto.getEmail())
						&& passwordEncoder.matches(ldto.getPassword(), user.getPassword())) {
					return true;
				}
				return false;

			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return false;
		}

	}

	@Override
	public ObjectNode processExcelData(MultipartFile excelFile) {
		return null;
	}

	public List<UserEntity> processExcelFile(MultipartFile file) throws IOException {
		List<UserEntity> users = new ArrayList<>();

		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();

			// Skipping the header row
			if (rowIterator.hasNext()) {
				rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				UserEntity user = new UserEntity();
				user.setEmail(getStringCellValue(row.getCell(2)));
				String encodedPassword = passwordEncoder.encode(getDateCellValue(row.getCell(4)));
				user.setPassword(encodedPassword);
				user.setFirstName(getStringCellValue(row.getCell(0)));
				user.setLastName(getStringCellValue(row.getCell(1)));
				user.setPhoneNumber(getStringCellValue(row.getCell(3)));
				user.setRoles("USER");
				user.setUsername(getStringCellValue(row.getCell(2)));

				repo.save(user);
			}
			return users;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;

	}

	private String getStringCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}

	private static String getDateCellValue(Cell cell) {
		if (cell == null || cell.getCellType() == CellType.BLANK) {
			return null;
		}

		// Check if the cell contains a numeric value (Excel date representation)
		if (DateUtil.isCellDateFormatted(cell)) {
			Date date = cell.getDateCellValue();
			// Format the date as a string (adjust the format as needed)
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			return dateFormat.format(date);
		} else if (cell.getCellType() == CellType.STRING) {
			// If the cell contains a string, assume it's already a formatted date
			return cell.getStringCellValue();
		} else {
			// If not a date or string, use DataFormatter to get the formatted date string
			DataFormatter dataFormatter = new DataFormatter();
			return dataFormatter.formatCellValue(cell);
		}
	}

	public List<SignupDTO> getAllUsers() {
		List<UserEntity> users = repo.findAll();
		return users.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private SignupDTO convertToDto(UserEntity user) {
		SignupDTO userDto = new SignupDTO();
		userDto.setEmail(user.getEmail());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRole(user.getRoles());
		userDto.setUsername(user.getUsername());
		return userDto;
	}
}