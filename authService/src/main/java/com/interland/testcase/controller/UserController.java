package com.interland.testcase.controller;

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.util.Elements.Origin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.dto.CompleteDTO;
import com.interland.testcase.entity.Role;
import com.interland.testcase.entity.User;
import com.interland.testcase.entity.UserRole;
import com.interland.testcase.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public User create(@RequestBody CompleteDTO completeDTO) {
		System.out.println("hello");
		
		User user=new User();
		user.setFirstName(completeDTO.getFirstName());
		user.setLastName(completeDTO.getLastName());
		user.setEmail(completeDTO.getEmail());
		user.setPhone(completeDTO.getPhone());
		user.setUsername(completeDTO.getUsername());
		
		Role role= new Role();
		role.setRoleId(completeDTO.getRoleId());
		role.setRoleName(completeDTO.getRoleName());
		
		Set<UserRole> userRoleSet=new HashSet<>();
		UserRole userRole= new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		
		userRoleSet.add(userRole);
		
		
		try {
			return userService.createUser(user, userRoleSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username ) {
		return userService.getUser(username);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		this.userService.deleteUser(userId);
	}
	
}
