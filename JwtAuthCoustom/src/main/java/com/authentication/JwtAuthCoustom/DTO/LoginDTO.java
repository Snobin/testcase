package com.authentication.JwtAuthCoustom.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginDTO {
	
	@NotEmpty(message = "{validation.login.email}")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",message = "{validation.login.email.pattern}")
	@Size(min = 1, max = 100, message = "{validation.login.email.size}")
	private String email;

	@NotEmpty(message = "{validation.login.password}")
	@Size(min = 3, max = 255, message = "{validation.login.password.size}")
	private String password;

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
