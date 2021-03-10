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
import org.springframework.data.domain.Page;
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

import com.tutofox.bootadmin.model.Project;
import com.tutofox.bootadmin.model.Role;
import com.tutofox.bootadmin.model.User;

import com.tutofox.bootadmin.service.UserService;
import com.tutofox.bootadmin.service.ProjectService;
import com.tutofox.bootadmin.controllers.UserController;

import com.tutofox.bootadmin.repository.ProjectRepository;
import com.tutofox.bootadmin.repository.UserRepository;
import com.tutofox.bootadmin.repository.RoleRepository;


@RestController
@RequestMapping("/project/")
public class ProjectController {
	
    private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(UserRepository userRepository, RoleRepository roleRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.projectRepository = projectRepository;
    }
    
    @Autowired
	private ProjectService projectService;

    @Autowired
	private UserService userService;

	@Autowired
	private UserController userController;

	@GetMapping("/page/{pageNum}")
	public ModelAndView viewPage(Model model,
			@PathVariable(name = "pageNum") int pageNum) {
		
		ModelAndView modelAndView = new ModelAndView();
		User userLogin = userService.findUserByUserName(userController.getUserLogin());
		modelAndView.addObject("userLogin", userLogin.getName() + " " + userLogin.getLastName());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());

		Page<Project> page = projectService.listAll(pageNum);
		List<Project> listProjects = page.getContent();
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
    	model.addAttribute("totalProjects", page.getTotalElements());
        model.addAttribute("projects", listProjects);

        modelAndView.setViewName("project/project");
		
		return modelAndView;
	}

    @GetMapping("list")
    public ModelAndView projectList(Model model) {
		return viewPage(model, 1);
    }

    @GetMapping(value="create")
	public ModelAndView createProject(Model model){
		ModelAndView modelAndView = new ModelAndView();
		User userLogin = userService.findUserByUserName(userController.getUserLogin());
		modelAndView.addObject("userLogin", userLogin.getName() + " " + userLogin.getLastName());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());
		
		Project project = new Project();
		modelAndView.addObject("project", project);
		modelAndView.setViewName("project/projectCreate");
		return modelAndView;
	}

    @PostMapping(value = "create")
	public ModelAndView createNewProject(@Valid Project project, BindingResult bindingResult, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		User userLogin = userService.findUserByUserName(userController.getUserLogin());
		modelAndView.addObject("userLogin", userLogin.getName() + " " + userLogin.getLastName());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());
		
		Project projectExists = projectService.findProjectName(project.getProjectName());
		if (projectExists != null) {
			bindingResult
					.rejectValue("projectName", "error.project",
							"There is already a project create with the project name provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("project/projectCreate");
		} else {
			projectService.saveProject(project);
			modelAndView.addObject("successMessage", "Project has been create successfully");
			modelAndView.addObject("project", new Project());

			return viewPage(model, 1);
		}
		
		return modelAndView;
	}

	@GetMapping("edit/{id}")
    public ModelAndView editProject(@PathVariable("id") Integer id, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		User userLogin = userService.findUserByUserName(userController.getUserLogin());
		modelAndView.addObject("userLogin", userLogin.getName() + " " + userLogin.getLastName());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());

        Optional<Project> optinalEntity  = projectRepository.findById(id);
	 	Project project = optinalEntity.get();

        model.addAttribute("project", project);
		modelAndView.setViewName("project/projectUpdate");
       
	   	return modelAndView;
    }

    @PostMapping("update/{id}")
    public ModelAndView updateProject(@PathVariable("id") Integer id, @Valid Project project, BindingResult result, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		User userLogin = userService.findUserByUserName(userController.getUserLogin());
		modelAndView.addObject("userLogin", userLogin.getName() + " " + userLogin.getLastName());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());

        if (result.hasErrors()) {
            project.setId(id);
            modelAndView.setViewName("project/projectUpdate");
       
	   		return modelAndView;
        }
        projectService.saveProject(project);
        
		return viewPage(model, 1);
    }

	@GetMapping("delete/{id}")
    public ModelAndView deleteProject(@PathVariable("id") Integer id, Model model) {
	 	ModelAndView modelAndView = new ModelAndView();
		User userLogin = userService.findUserByUserName(userController.getUserLogin());
		modelAndView.addObject("userLogin", userLogin.getName() + " " + userLogin.getLastName());
		modelAndView.addObject("userLoginRole", userController.getUserLoginRole());
		
	 	Optional<Project> optinalEntity  = projectRepository.findById(id);
	 	Project project = optinalEntity.get();
        projectRepository.delete(project);

		return viewPage(model, 1);
    }
}
