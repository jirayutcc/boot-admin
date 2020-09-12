package com.tutofox.bootadmin.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutofox.bootadmin.models.User;
import com.tutofox.bootadmin.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "/user")
	public List<User> test() {
		return userRepository.findAll();
	}
	
	@PostMapping(value="/create")
	public ResponseEntity<String> create(@RequestBody User data){
		
		try {
			userRepository.save(data);
			return new ResponseEntity<>("save [User] successful " , HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("error : " + e , HttpStatus.INTERNAL_SERVER_ERROR);
		}

 	}
	
}
