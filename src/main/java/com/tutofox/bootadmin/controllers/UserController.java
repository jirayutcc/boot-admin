package com.tutofox.bootadmin.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping(value="/list")
	public Map<String, Object> list(){
		
		HashMap<String,Object> response = new HashMap<String,Object>();
		
		try { 
			List<User> userList; 
			userList = userRepository.findAll();
			response.put("message", "Successful load");
			response.put("list", userList);
			response.put("success", true);
			return response;
			
		} catch (Exception e) {  
			response.put("message", e.getMessage()); 
			response.put("success ", false);
			return response;
		}
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
	
	@GetMapping(value = "get/{id}" )
	public Map<String, Object> data(@PathVariable("id") Integer id){
		
		HashMap<String,Object> response = new HashMap<String,Object>();
		
		try {   
			
			Optional<User> user = userRepository.findById(id);  
		 
			if (user.isPresent()) { 
				response.put("message", "Successful load");
				response.put("data", user);
				response.put("success", true);
				return response;
			}
			else {
				response.put("message", "Not found data");
				response.put("data", null);
				response.put("success", false);
				return response;
			}
			
		} catch (Exception e){ 
			response.put("message", e.getMessage()); 
			response.put("success", false);
			return response;
		}
	}
	
	@PutMapping(value="/update/{id}")  
	public Map<String, Object> update(@PathVariable("id") Integer id,
			@RequestBody User data ){
		
		HashMap<String,Object> response = new HashMap<String,Object>();
		
		try {  
			data.setId(id);
			userRepository.save(data);
			response.put("message", "Successful update");
			response.put("success", true);
			return response;
		} catch (Exception e) {
			response.put("message", e.getMessage()); 
			response.put("success", false);
			return response;
		}
		
	}

	@DeleteMapping(value="/delete/{id}")
	public Map<String, Object> update(@PathVariable("id") Integer id){
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		try {  
			userRepository.deleteById(id);;
			response.put("message","Successful delete"); 
			response.put("success", true);
			return response; 
		} catch (Exception e) {
			response.put("message",e.getMessage()); 
			response.put("success", false);
			return response;
		}
		
	}
}
