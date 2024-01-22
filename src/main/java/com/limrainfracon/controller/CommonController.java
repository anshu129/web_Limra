package com.limrainfracon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.limrainfracon.model.User;
import com.limrainfracon.request.EmailRequest;
import com.limrainfracon.service.UserService;

@Controller
public class CommonController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String homePage() {
		return "index";
	}
	
	@GetMapping("/loginPage")
	public String homeLogin(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	@GetMapping("/signup")
	public String homeSignup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@GetMapping("/withdrawal")
	public String getWithdrawl(Model model) {
		model.addAttribute("user", new User());
		return "withdrawal";
	}
	@GetMapping("/My_Profile")
	public String getSettings(Model model) {
		model.addAttribute("user", new User());
		return "settings";
	}
	@GetMapping("/history")
	public String getInstallment(Model model) {
		model.addAttribute("user", new User());
		return "installment";
	}
	@GetMapping("/transport")
	public String getTransport(Model model) {
		model.addAttribute("user", new User());
		return "transport";
	}
	@GetMapping("/projectmap")
	public String getProjectmap(Model model) {
		model.addAttribute("user", new User());
		return "projectmap";
	}
	
	@GetMapping("/propertysell")
	public String getSell(Model model) {
		model.addAttribute("user", new User());
		return "sell";
	}
	@PostMapping("/sendConcernEmail")
	@ResponseBody
	public ResponseEntity<?> sendEmailToUs(@RequestBody EmailRequest emailRequest){
		
		//String message ="";
		try {
			
			userService.sendEmailToUs(emailRequest);
			//model.addAttribute("message", "Mail sent successfully.");
			//message = "Mail sent successfully.";
			
		}catch(Exception e) {
			e.printStackTrace();
			//error
		}
		return new ResponseEntity<>("Mail sent successfully.", HttpStatus.OK);
		
	}
}
