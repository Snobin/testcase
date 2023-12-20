package com.interland.testcase.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.testcase.entity.Role;
import com.interland.testcase.entity.User;
import com.interland.testcase.repository.RoleRepo;
import com.interland.testcase.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private RoleRepo roleRepo;

	 public User registerNewUser(User user) {
//	        Role role = roleDao.findById("User").get();
//	        Set<Role> userRoles = new HashSet<>();
//	        userRoles.add(role);
//	        user.setRole(userRoles);
//	        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

	        return repo.save(user);
	    }
	 public void initRoleAndUser() {

	        Role adminRole = new Role();
	        adminRole.setRoleName("Admin");
	        adminRole.setRoleDescription("Admin role");
	        roleRepo.save(adminRole);

	        Role userRole = new Role();
	        userRole.setRoleName("User");
	        userRole.setRoleDescription("Default role for newly created record");
	        roleRepo.save(userRole);

	        User adminUser = new User();
	        adminUser.setUserName("admin123");
	        adminUser.setUserPassword("admin@p");
	        adminUser.setUserFirstName("admin");
	        adminUser.setUserLastName("admin");
	        Set<Role> adminRoles = new HashSet<>();
	        adminRoles.add(adminRole);
	        adminUser.setRole(adminRoles);
	        repo.save(adminUser);
	        
	        User user = new User();
	        user.setUserName("user123");
	        user.setUserPassword("user@p");
	        user.setUserFirstName("user");
	        user.setUserLastName("user");
	        Set<Role> userRoles = new HashSet<>();
	        userRoles.add(userRole);
	        user.setRole(userRoles);
	        repo.save(user);
	        
	 }
}
