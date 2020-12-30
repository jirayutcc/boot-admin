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

	public String getUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUserName(auth.getName());
		
        return user.getUserName();
    }

	public String getUserLoginRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUserName(auth.getName());
		String userRole = userService.getRoleName(user.getRoles());
		
        return userRole;
    }

    @GetMapping("list")
    public ModelAndView userList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", getUserLogin());
		modelAndView.addObject("userLoginRole", getUserLoginRole());
		
        model.addAttribute("users", userRepository.findAll());
        modelAndView.setViewName("user/user");
		return modelAndView;
    }

    @GetMapping(value="create")
	public ModelAndView registration(Model model){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", getUserLogin());
		modelAndView.addObject("userLoginRole", getUserLoginRole());
		
		User user = new User();
		modelAndView.addObject("user", user);
		List<Role> roles =  roleRepository.findAll();
		model.addAttribute("roles", roles);
		modelAndView.setViewName("user/userCreate");
		return modelAndView;
	}

    @PostMapping(value = "create")
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", getUserLogin());
		modelAndView.addObject("userLoginRole", getUserLoginRole());
		
		User userExists = userService.findUserByUserName(user.getUserName());
		if (userExists != null) {
			bindingResult
					.rejectValue("userName", "error.user",
							"There is already a user registered with the user name provided");
		}
		if (bindingResult.hasErrors()) {
			List<Role> roles =  roleRepository.findAll();
			model.addAttribute("roles", roles);
			modelAndView.setViewName("user/userCreate");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			model.addAttribute("users", userRepository.findAll());
			modelAndView.setViewName("user/user");

		}
		return modelAndView;
	}

	@GetMapping("edit/{id}")
    public ModelAndView editUser(@PathVariable("id") Integer id, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", getUserLogin());
		modelAndView.addObject("userLoginRole", getUserLoginRole());

        Optional<User> optinalEntity  = userRepository.findById(id);
	 	User user = optinalEntity.get();
		
		int roleId = userService.getRoleId(user.getRoles());
		Role role = userService.findRoleById(roleId);

		List<Role> roles =  roleRepository.findAll();
		model.addAttribute("sRole", role);
		model.addAttribute("roles", roles);
        model.addAttribute("user", user);
		modelAndView.setViewName("user/userUpdate");
       
	   	return modelAndView;
    }

    @PostMapping("update/{id}")
    public ModelAndView updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", getUserLogin());
		modelAndView.addObject("userLoginRole", getUserLoginRole());

        if (result.hasErrors()) {
            user.setId(id);
			
			int roleId = userService.getRoleId(user.getRoles());
			Role role = userService.findRoleById(roleId);

			List<Role> roles =  roleRepository.findAll();
			model.addAttribute("sRole", role);
			model.addAttribute("roles", roles);
            modelAndView.setViewName("user/userUpdate");
       
	   		return modelAndView;
        }

        userService.saveUser(user);
        model.addAttribute("users", userRepository.findAll());
		modelAndView.setViewName("user/user");
        
		return modelAndView;
    }

	@GetMapping("delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Integer id, Model model) {
	 	ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", getUserLogin());
		modelAndView.addObject("userLoginRole", getUserLoginRole());
		
	 	Optional<User> optinalEntity  = userRepository.findById(id);
	 	User user = optinalEntity.get();
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        modelAndView.setViewName("user/user");

		return modelAndView;
    }
}
