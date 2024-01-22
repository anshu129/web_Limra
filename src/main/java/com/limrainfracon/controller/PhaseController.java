package com.limrainfracon.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.limrainfracon.model.Phase;
import com.limrainfracon.model.Project;
import com.limrainfracon.model.User;
import com.limrainfracon.service.PhaseService;
import com.limrainfracon.service.ProjectService;

@Controller
@RequestMapping("/admin")
public class PhaseController {
	
	@Autowired
    private  PhaseService phaseService;
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping("/addPhase")
    public String showAddPhaseForm(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        model.addAttribute("user", user);
        model.addAttribute("phase", new Phase());
        return "add_phase";
    }

    @PostMapping("/addPhase")
    public String addPhaseToProject(@Valid @ModelAttribute Phase phase,BindingResult bindingResult, Model model, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute("user");
    	model.addAttribute("user", user);
    	if (bindingResult.hasErrors()) {
		        return "add_phase";
		    }
    	Phase existingPhase =  phaseService.findPhaseByPhaseName(phase.getPhaseName());
    	if(existingPhase!=null) {
    		model.addAttribute("message","Phase With This Name Already Exist.");
    	}else {
    		Phase savedPhase = phaseService.addPhaseToProject(phase.getProject().getProjectId(), phase);
            model.addAttribute("phase", savedPhase);
            model.addAttribute("phase", new Phase());
            model.addAttribute("message","Phase Added Sucessfully.");
    	}
    	List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "add_phase";
    }
    
    @GetMapping("/getPhases")
    @ResponseBody
    public List<Phase> getPhases(@RequestParam(name = "projectId") String projectId) {
        return phaseService.getPhasesByProjectId(projectId);
    }
}
