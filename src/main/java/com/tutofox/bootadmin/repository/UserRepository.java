package com.tutofox.bootadmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutofox.bootadmin.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	// List<User> findByUsername(String name);
	List<User> findByUsernameLike(String name);

	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String name);
}
