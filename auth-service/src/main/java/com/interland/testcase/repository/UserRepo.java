package com.interland.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.User;

public interface UserRepo extends JpaRepository<User, String> {

}
