package com.interland.testcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.dto.userdto;
import com.interland.testcase.entity.Role;
import com.interland.testcase.service.RoleService;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@PostMapping({"/createNewRole"})
	public Role createNewRole(@RequestBody userdto role) {
		System.out.println(role.toString());
		return roleService.createNewRole(role);
	}
}
