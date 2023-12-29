package com.authentication.JwtAuthCoustom.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authentication.JwtAuthCoustom.Entity.UserEntity;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, Long>
{

	Optional<UserEntity> findIdByEmail(String username);
	
	

}