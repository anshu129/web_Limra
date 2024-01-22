package com.limrainfracon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.limrainfracon.model.Project;
import com.limrainfracon.model.User;
import com.limrainfracon.service.ProjectService;
import com.limrainfracon.service.UserService;

@Controller
@RequestMapping("/admin")
public class ProjectController {
	
	@Autowired
	UserService userService;
	
	@Autowired 
	private ProjectService projectService;
	
	
	  @GetMapping("/addProject")
	    public String addProjectForm(Model model, HttpServletRequest request) {
		    User user = (User) request.getSession().getAttribute("user");
	        model.addAttribute("project", new Project());
	        model.addAttribute("user", user);
	        return "add_project";
	    }

	 @PostMapping("/addProject")
	    public String saveProject(@Valid @ModelAttribute("project") Project project,BindingResult bindingResult, Model model, HttpServletRequest request) {
		 User user = (User) request.getSession().getAttribute("user");
		 model.addAttribute("user", user);
		 if (bindingResult.hasErrors()) {
		        return "add_project";
		    }
		   Project existingProject =  projectService.findProjectByProjectName(project.getProjectName());
		   if(existingProject != null) {
			  model.addAttribute("message","Project With This Name Already Exist.");
		  }else {
			  projectService.saveProject(project);
			  model.addAttribute("project", new Project());
		      model.addAttribute("message","Project Added Sucessfully.");
		  }
	       return "add_project";
	    }
}
