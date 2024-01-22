package com.limrainfracon.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.limrainfracon.model.Project;
import com.limrainfracon.model.TransportDetails;
import com.limrainfracon.model.User;
import com.limrainfracon.request.EmailRequest;
import com.limrainfracon.service.AdminService;
import com.limrainfracon.service.ProjectService;

import com.limrainfracon.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired 
	private ProjectService projectService;

	@GetMapping("/dashboard")
	public String getAdmindashboard(Model model,HttpServletRequest request) {
		model.addAttribute("user", (User) request.getSession().getAttribute("user"));
		List<User> userList = userService.getAllUsers();
		Long todalUserCount = (long) userList.size();
		model.addAttribute("totalUserCount", todalUserCount);
		model.addAttribute("userList", userList);
		return "admindashboard";
	}
	
	/*
	 * @GetMapping("/addProject") public String addProjectForm(Model model) {
	 * model.addAttribute("project", new Project()); return "add_project"; }
	 */
		/*
		 * @PostMapping("/addProject") public String
		 * saveProject(@ModelAttribute("project") Project project, Model model) {
		 * projectService.saveProject(project);
		 * model.addAttribute("message","Project Added Sucessfully."); return
		 * "add_project"; }
		 */
	 @GetMapping("/Projects")
	    public String goToCompanyProjects(Model model, HttpServletRequest request) {
	        //model.addAttribute("project", new Project());
	        model.addAttribute("user", (User) request.getSession().getAttribute("user"));
	        return "Company_Projects";
	    }
	   @GetMapping("/Transport")
	    public String goToCompanyTransport(Model model, HttpServletRequest request) {
		 	model.addAttribute("user", (User) request.getSession().getAttribute("user"));
	        List<TransportDetails> transportDetails = adminService.getAllTransportDetails();
	        model.addAttribute("transportDetails", transportDetails);
	        return "Admin_Transport";
	    }
	   
	    @PostMapping("/updateTransportStatus")
		@ResponseBody
		public ResponseEntity<?> updateTransportStatus(@RequestParam("id") String  id, @RequestParam("Status") String status){
			
	    	String message = status+" successfully.";
			try {
				adminService.updateTransportStatus(id, status);
				
				
			}catch(Exception e) {
				e.printStackTrace();
				message  = "Error while "+status;
				return new ResponseEntity<>(message, HttpStatus.OK);
			}
			return new ResponseEntity<>(message, HttpStatus.OK);
			
		}

}
