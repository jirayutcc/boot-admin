package com.tutofox.bootadmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutofox.bootadmin.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmail(String email);
	User findByUserName(String userName);
}
