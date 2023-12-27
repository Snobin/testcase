package com.interland.testcase.service;

import java.util.Set;

import com.interland.testcase.entity.User;
import com.interland.testcase.entity.UserRole;

public interface UserService {
	
	public User createUser(User user,Set<UserRole> userRoles)throws Exception;
	
	public User getUser(String username);
	
	public void deleteUser(Long userId);

}
