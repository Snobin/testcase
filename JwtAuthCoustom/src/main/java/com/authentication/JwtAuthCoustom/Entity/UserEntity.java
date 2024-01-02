package com.authentication.JwtAuthCoustom.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="JWTAuthentication_Registration")
public class UserEntity 
{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,name="EMAIL")
    private String email;

    @Column(nullable = false,name="PASSWORD")
    private String password;

    @Column(nullable = false,name="NAME")
    private String name;

    @Override
	public String toString() {
		return "UserEntity [id=" + id + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", phoneNumber=" + phoneNumber + ", Roles=" + Roles + ", username=" + username + "]";
	}

	@Column(nullable = false,name="PHONE-NUMBER")
    private String phoneNumber;
    
    @Column(nullable = false,name="ROLE")
    private String Roles;
    
    private String username;
    
    
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoles() {
		return Roles;
	}

	public void setRoles(String role) {
		Roles = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}

