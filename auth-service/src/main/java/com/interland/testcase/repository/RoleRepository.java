package com.interland.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
