package com.interland.testcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.userdto;
import com.interland.testcase.entity.Role;
import com.interland.testcase.repository.RoleRepo;

@Service
public class RoleService {

	@Autowired
	private RoleRepo roleRepo;
	
	public Role createNewRole(userdto role) {
		Role rol=new Role();
		rol.setRoleDescription(role.getRoleDescription());
		rol.setRoleName(role.getRoleName());
		return roleRepo.save(rol);
	}
}
