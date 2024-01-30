package com.authentication.JwtAuthCoustom.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginDTO {
	@NotNull(message = "{validation.login.name}")
	@Size(min = 1, max = 50, message = "{validation.login.name.size}")
	private String name;

	@NotEmpty(message = "{validation.login.email}")
	@Size(min = 1, max = 100, message = "{validation.login.email.size}")
	private String email;

	@NotEmpty(message = "{validation.login.password}")
	@Size(min = 1, max = 255, message = "{validation.login.password.size}")
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
