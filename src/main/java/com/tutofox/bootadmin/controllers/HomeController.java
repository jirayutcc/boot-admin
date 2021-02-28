package com.tutofox.bootadmin.controllers;

import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;

import com.tutofox.bootadmin.model.User;
import com.tutofox.bootadmin.model.Role;
import com.tutofox.bootadmin.model.TimeSheet;
import com.tutofox.bootadmin.controllers.UserController;
import com.tutofox.bootadmin.service.UserService;
import com.tutofox.bootadmin.service.TimeSheetService;
import com.tutofox.bootadmin.repository.UserRepository;
import com.tutofox.bootadmin.repository.RoleRepository;
import com.tutofox.bootadmin.repository.TimeSheetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;

import javax.validation.Valid;

import org.springframework.ui.Model;

@Controller
public class HomeController {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final TimeSheetRepository timeSheetRepository;

    @Autowired
    public HomeController(UserRepository userRepository, RoleRepository roleRepository, TimeSheetRepository timeSheetRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
		this.timeSheetRepository = timeSheetRepository;
    }

	@Autowired
	private UserController userController;
	
	@Autowired
	private UserService userService;

	@Autowired
	private TimeSheetService timeSheetService;

	@GetMapping(value={"/", "/login"})
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@GetMapping(value={"/dashboard"})
	public ModelAndView dashboard(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", userController.getUserLogin());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());

		modelAndView.addObject("timeSheets", timeSheetRepository.findAll());
		modelAndView.addObject("users", userRepository.findAll());

		modelAndView.setViewName("dashboard");
		return modelAndView;
	}

	@GetMapping(value={"/profile"})
	public ModelAndView profile(Model model){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", userController.getUserLogin());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());
		User user = userService.findUserByUserName(userController.getUserLogin());
		modelAndView.addObject("userName", user.getName() + " " + user.getLastName());

		Optional<User> optinalEntity  = userRepository.findById(user.getId());
	 	User userModel = optinalEntity.get();

		model.addAttribute("user", userModel);

		modelAndView.setViewName("profile");
		return modelAndView;
	}

	@PostMapping("/profile/update/{id}")
    public ModelAndView updateProfile(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", userController.getUserLogin());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());
		User getUserName = userService.findUserByUserName(userController.getUserLogin());
		modelAndView.addObject("userName", getUserName.getName() + " " + getUserName.getLastName());

		User role = userService.findUserByUserName(userController.getUserLogin());

        if (result.hasErrors()) {
            user.setId(id);
            modelAndView.setViewName("profile");
	   		return modelAndView;
        }
		user.setRoles(role.getRoles());
        userService.saveUser(user);
		modelAndView.setViewName("profile");
        
		return modelAndView;
    }

	// @GetMapping(value="/admin/home")
	// public ModelAndView home(){
	// 	ModelAndView modelAndView = new ModelAndView();
	// 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	// 	User user = userService.findUserByUserName(auth.getName());
	// 	modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
	// 	modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
	// 	modelAndView.setViewName("admin/home");
	// 	return modelAndView;
	// }

	@GetMapping("/timesheet/page/{pageNum}")
	public ModelAndView viewTimesheetPage(Model model,
			@PathVariable(name = "pageNum") int pageNum) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", userController.getUserLogin());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());

		Page<TimeSheet> page = timeSheetService.listAll(pageNum);
		List<TimeSheet> listTimesheets = page.getContent();
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
    	model.addAttribute("totalTimeSheets", page.getTotalElements());
        model.addAttribute("timesheets", listTimesheets);

        modelAndView.setViewName("timesheet/timesheet");
		
		return modelAndView;
	}

	@GetMapping("/timesheet")
    public ModelAndView timeSheetList(Model model) {
		return viewTimesheetPage(model, 1);
    }

	@GetMapping("/timesheet/delete/{id}")
    public ModelAndView deleteTimesheet(@PathVariable("id") Integer id, Model model) {
	 	ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userLogin", userController.getUserLogin());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());
		
	 	Optional<TimeSheet> optinalEntity  = timeSheetRepository.findById(id);
	 	TimeSheet timesheet = optinalEntity.get();
        timeSheetRepository.delete(timesheet);

		return viewTimesheetPage(model, 1);
    }
}
