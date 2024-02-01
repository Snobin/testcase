package com.authentication.JwtAuthCoustom.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupDTO 
{
	@NotEmpty(message = "{validation.signup.firstName}")
    @Size(min = 3, max = 100, message = "{validation.signup.firstName.size}")
    private String firstName;

    @NotEmpty(message = "{validation.signup.lastName}")
    @Size(min = 3, max = 100, message = "{validation.signup.lastName.size}")
    private String lastName;

    @NotEmpty(message = "{validation.signup.email}")
    private String email;

    @NotEmpty(message = "{validation.signup.phoneNumber}")
    @Pattern(regexp = "[0-9]+", message = "{validation.signup.phoneNumber.pattern}")
    @Size(min = 10, max = 15, message = "{validation.signup.phoneNumber.size}")
    private String phoneNumber;

    @NotEmpty(message = "{validation.signup.password}")
    private String password;

    @NotEmpty(message = "{validation.signup.role}")
    private String role;
    
    @NotEmpty(message = "{validation.signup.username}")
    @Size(min = 3, max = 15, message = "{validation.signup.username.size}")
    private String username;
    
    
    
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}