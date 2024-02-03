package com.authentication.JwtAuthCoustom.DTO;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.authentication.JwtAuthCoustom.Entity.UserEntity;

public class CustomUserDetails implements UserDetails
{   
	private String username;
	private String email;
	private String firstName;
	private String lastName;
    private String password;
    private String phoneNumber;
    private String role;
	private Set<? extends GrantedAuthority> authorities;
    

	public void setUsername(String username) {
		this.username = username;
	}
	
    public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	public CustomUserDetails(String username,String email, String password, Set<? extends GrantedAuthority> authorities, String phoneNumber,String role, String firstName,String lastName) {
        this.username=username;
		this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.phoneNumber = phoneNumber;
        this.role=role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

	public CustomUserDetails(UserEntity details) {
	}

}
