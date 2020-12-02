package com.tutofox.bootadmin.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tutofox.bootadmin.model.Role;
import com.tutofox.bootadmin.model.User;

import com.tutofox.bootadmin.service.UserService;

import com.tutofox.bootadmin.repository.UserRepository;
import com.tutofox.bootadmin.repository.RoleRepository;


@RestController
@RequestMapping("/user/")
public class UserController {
	
    private final UserRepository userRepository;
	private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    
    @Autowired
	private UserService userService;

    @GetMapping("list")
    public ModelAndView userList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("users", userRepository.findAll());
        modelAndView.setViewName("user");
		return modelAndView;
    }

    @GetMapping(value="create")
	public ModelAndView registration(Model model){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// User user = userService.findUserByUserName(auth.getName());
		User user = new User();
		modelAndView.addObject("user", user);
		List<Role> roles =  roleRepository.findAll();
		model.addAttribute("roles", roles);
		modelAndView.setViewName("userCreate");
		return modelAndView;
	}

    @PostMapping(value = "create")
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// User user = userService.findUserByUserName(auth.getName());
		User userExists = userService.findUserByUserName(user.getUserName());
		if (userExists != null) {
			bindingResult
					.rejectValue("userName", "error.user",
							"There is already a user registered with the user name provided");
		}
		if (bindingResult.hasErrors()) {
			List<Role> roles =  roleRepository.findAll();
			model.addAttribute("roles", roles);
			modelAndView.setViewName("userCreate");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			model.addAttribute("users", userRepository.findAll());
			modelAndView.setViewName("user");

		}
		return modelAndView;
	}

	@GetMapping("edit/{id}")
    public ModelAndView editUser(@PathVariable("id") Integer id, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// User user = userService.findUserByUserName(auth.getName());
        Optional<User> optinalEntity  = userRepository.findById(id);
	 	User user = optinalEntity.get();
		
		int roleId = userService.getRoleId(user.getRoles());
		Role role = userService.findRoleById(roleId);

		List<Role> roles =  roleRepository.findAll();
		model.addAttribute("sRole", role);
		model.addAttribute("roles", roles);
        model.addAttribute("user", user);
		modelAndView.setViewName("userUpdate");
       
	   	return modelAndView;
    }

    @PostMapping("update/{id}")
    public ModelAndView updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// User user = userService.findUserByUserName(auth.getName());
        if (result.hasErrors()) {
            user.setId(id);
			
			int roleId = userService.getRoleId(user.getRoles());
			Role role = userService.findRoleById(roleId);

			List<Role> roles =  roleRepository.findAll();
			model.addAttribute("sRole", role);
			model.addAttribute("roles", roles);
            modelAndView.setViewName("userUpdate");
       
	   		return modelAndView;
        }

        userService.saveUser(user);
        model.addAttribute("users", userRepository.findAll());
		modelAndView.setViewName("user");
        
		return modelAndView;
    }

	@GetMapping("delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Integer id, Model model) {
	 	ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// User user = userService.findUserByUserName(auth.getName());
	 	Optional<User> optinalEntity  = userRepository.findById(id);
	 	User user = optinalEntity.get();
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        modelAndView.setViewName("user");

		return modelAndView;
    }

	// @Autowired
	// UserRepository userRepository;

	// @GetMapping(value = "/")
	// public List<User> userAll() {
	// 	return userRepository.findAll();
	// }

    // @GetMapping(value={"/"})
	//     public ModelAndView user(){
	// 	ModelAndView modelAndView = new ModelAndView();
	// 	modelAndView.setViewName("user");
	// 	return modelAndView;
	// }
//
//	@GetMapping(value="/list")
//	public Map<String, Object> list(){
//
//		HashMap<String,Object> response = new HashMap<String,Object>();
//
//		try {
//			List<User> userList;
//			userList = userRepository.findAll();
//			response.put("message", "Successful load");
//			response.put("list", userList);
//			response.put("success", true);
//			return response;
//
//		} catch (Exception e) {
//			response.put("message", e.getMessage());
//			response.put("success ", false);
//			return response;
//		}
//	}
//
//	@PostMapping(value="/create")
//	public Map<String, Object> create(@Valid @RequestBody User data){
//
//		HashMap<String, Object> response = new HashMap<String, Object>();
//
//		try {
//			Optional<User> validEmail = userRepository.findByEmail(data.getEmail());
//			Optional<User> validUsername = userRepository.findByUsername(data.getUsername());
//
//			if (validUsername.isPresent()) {
//				response.put("message", "The username " + data.getUsername() + " is already registered ");
//				response.put("success", false);
//				return response;
//			} else if (validEmail.isPresent()) {
//				response.put("message", "The email " + data.getEmail() + " is already registered ");
//				response.put("success", false);
//				return response;
//			} else {
//				userRepository.save(data);
//				response.put("message", "Successful save");
//				response.put("success", true);
//				return response;
//			}
//		} catch (Exception e) {
//			response.put("message", e.getMessage());
//			response.put("success", false);
//			return response;
//		}
//	}
//
//	@GetMapping(value = "get/{id}" )
//	public Map<String, Object> data(@PathVariable("id") Integer id){
//
//		HashMap<String,Object> response = new HashMap<String,Object>();
//
//		try {
//
//			Optional<User> user = userRepository.findById(id);
//
//			if (user.isPresent()) {
//				response.put("message", "Successful load");
//				response.put("data", user);
//				response.put("success", true);
//				return response;
//			}
//			else {
//				response.put("message", "Not found data");
//				response.put("data", null);
//				response.put("success", false);
//				return response;
//			}
//
//		} catch (Exception e){
//			response.put("message", e.getMessage());
//			response.put("success", false);
//			return response;
//		}
//	}
//
//	@PutMapping(value="/update/{id}")
//	public Map<String, Object> update(@PathVariable("id") Integer id,
//			@RequestBody User data ){
//
//		HashMap<String,Object> response = new HashMap<String,Object>();
//
//		try {
//			data.setId(id);
//			userRepository.save(data);
//			response.put("message", "Successful update");
//			response.put("success", true);
//			return response;
//		} catch (Exception e) {
//			response.put("message", e.getMessage());
//			response.put("success", false);
//			return response;
//		}
//	}
//
//	@DeleteMapping(value="/delete/{id}")
//	public Map<String, Object> update(@PathVariable("id") Integer id){
//
//		HashMap<String, Object> response = new HashMap<String, Object>();
//
//		try {
//			userRepository.deleteById(id);;
//			response.put("message","Successful delete");
//			response.put("success", true);
//			return response;
//		} catch (Exception e) {
//			response.put("message",e.getMessage());
//			response.put("success", false);
//			return response;
//		}
//	}
}
