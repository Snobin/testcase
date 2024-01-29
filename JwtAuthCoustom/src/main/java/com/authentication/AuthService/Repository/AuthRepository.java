package com.authentication.AuthService.Repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.authentication.AuthService.Entity.UserEntity;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, Long>
{

	Optional<UserEntity> findByEmail(String email);
	
//	@Query("SELECT u FROM JWTAuthentication_Registration u WHERE u.username = ?1")
    Optional<UserEntity> findByUsername(String username);	
    
    List<UserEntity> findAll();
	

}