package com.interland.testcase.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.testcase.entity.User;
import com.interland.testcase.entity.UserRole;
import com.interland.testcase.repository.RoleRepository;
import com.interland.testcase.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService{

	@Autowired 
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
		User local = userRepository.findByUsername(user.getUsername());
		if(local!=null) {
			System.out.println("user already present");
			throw new Exception("User Already Present");
		}else {
			for(UserRole ur : userRoles) {
				this.roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			local=this.userRepository.save(user);
		}
		return local;
	}
	

}
