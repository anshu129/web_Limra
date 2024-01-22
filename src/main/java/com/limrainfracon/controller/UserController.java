package com.limrainfracon.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.limrainfracon.model.Role;
import com.limrainfracon.model.TransportClients;
import com.limrainfracon.model.TransportDetails;
import com.limrainfracon.model.User;
import com.limrainfracon.model.Userdocs;
import com.limrainfracon.request.MyChartDetails;
import com.limrainfracon.response.CommonResponse;
import com.limrainfracon.service.UserService;
import com.limrainfracon.uility.CreateFolderUtility;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/saveUser")
	public String saveUser(@Valid @ModelAttribute User user,BindingResult bindingResult, Model model) {
		 if (bindingResult.hasErrors()) {
		        return "signup";
		    }
		CommonResponse response = userService.save(user);
		if (response.isSuccess()) {
            model.addAttribute("message", response.getMessage());
            model.addAttribute("user", new User());
            return "signup";
        } else {
            model.addAttribute("message", response.getMessage());
            return "signup";
        }
	}
	
	@PostMapping("/login")
	public String userLogin(@ModelAttribute User user, Model model) {
	    User usr = userService.findUser(user);
	    if(usr == null) {
	        model.addAttribute("message","User does not exist.");
	        model.addAttribute("user", new User());
	        return "login";
	    }
	    Set<Role> roles = usr.getRoles();
	    String roleName=null;
	    for(Role role : roles) {
	        roleName= role.getRoleName();
	    }
	    if(roleName.equalsIgnoreCase("ADMIN")) {
	        return "redirect:/admin/dashboard";
	    }

	    return "redirect:/user/dashboard";
	}
	
	@GetMapping("/dashboard")
	public String getUserDashboardDetails(HttpServletRequest request, Model model) {
	    model.addAttribute("user", (User) request.getSession().getAttribute("user"));
	    User user =  (User) request.getSession().getAttribute("user");
	    Set<Role> roles = user.getRoles();
	    String roleName=null;
	    for(Role role : roles) {
	        roleName= role.getRoleName();
	    }
	    if(roleName.equalsIgnoreCase("ADMIN")) {
	        return "redirect:/admin/dashboard";
	    }
	    return "dashboard";
	}

	@GetMapping("/My_Profile")
	public String getUser(Model model, HttpServletRequest request){
		//Optional<User> usr = userService.userFindById(userId);
		model.addAttribute("user", (User) request.getSession().getAttribute("user"));
		return "My_Profile";
	}
	@GetMapping("/Users")
	public List<User> getAllUsers(){
		
		List<User> users = userService.getAllUsers();
		return users;
	}
	
	@GetMapping("/MyUsers")
	public List<User> getAllMyUsers(@RequestParam("loginId") String loginId){
		
		List<User> users = userService.getAllMyUsers(loginId);
		return users;
	}
	
	@GetMapping("/Update_KYC")
	public String getKycPage(HttpServletRequest request, Model model) {
		User user = (User) request.getSession().getAttribute("user");
		Userdocs userdocs = userService.getAllDetailsById(user.getLoginId());
		model.addAttribute("user", user);
		if(userdocs != null)
			model.addAttribute("docfilePath", userdocs.getFilePath());
		return "User_KYC";
	}
	
	@PostMapping(value = "/saveFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String saveFile(@RequestParam(value="file", required=false) MultipartFile file,@RequestParam(value="id_number", required=false)String id_Number,HttpSession session, Model model, HttpServletRequest request)throws Exception {
		        model.addAttribute("user", (User) request.getSession().getAttribute("user"));
		        String path = request.getContextPath();
			    User usr = (User) request.getSession().getAttribute("user");
			    if(usr == null) {
			    	model.addAttribute("user", new User());
			    	return "login";
			    }
			    final File TEMP_DIRECTORY = new File(path);
			    File newDirectory = new File(TEMP_DIRECTORY, "/upload/"+usr.getLoginId()+"/");
			    boolean isExist = CreateFolderUtility.checkAndCreateDirectory(newDirectory);
			    if(!isExist){
					throw new Exception("Not have sufficient privileges to create folder on server");
				}
		        String filename=file.getOriginalFilename();  
		        long fileSize = file.getSize();
		        String filePath=newDirectory+"/"+filename;
		        System.out.println(newDirectory+"/"+filename);
		        File f = new File(filePath);
		        boolean isFileExist = f.exists();
		        if(isFileExist) {
		        	 model.addAttribute("msg", "File already exist");
		        	 Userdocs userdocs = userService.getAllDetailsById(usr.getLoginId());
		    		 model.addAttribute("docfilePath", userdocs.getFilePath()+"/"+userdocs.getFileName());
			         model.addAttribute("id_number", id_Number);
		        }else {
			        try{  
			        byte barr[]=file.getBytes();  
			          
			        BufferedOutputStream bout=new BufferedOutputStream(  
			                 new FileOutputStream(newDirectory+"/"+filename));  
			        bout.write(barr); 
			        if(!isFileExist) {
			        	//throw new Exception("File already exist.");
			        	//model.addAttribute("msg", "File already exist");
			        	userService.saveDocsDetails(filename, fileSize, filePath, usr.getLoginId(), id_Number, "file");
			        }
			        bout.flush();  
			        bout.close();
			        Userdocs userdocs = userService.getAllDetailsById(usr.getLoginId());
		    		model.addAttribute("docfilePath", userdocs.getFilePath());
			        model.addAttribute("id_number", id_Number);
			        model.addAttribute("msg", "file uploaded successfully.");
			        
			        }catch(Exception e) {
			        	System.out.println(e);
			        }
		        }     
		
		return "User_KYC";
	}

	@PostMapping(value = "/saveImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String saveImage(@RequestParam(value="file", required=false) MultipartFile file,HttpSession session, Model model, HttpServletRequest request) throws Exception {
		
		model.addAttribute("user", (User) request.getSession().getAttribute("user"));
        String path = request.getContextPath();
	    User usr = (User) request.getSession().getAttribute("user");
	    final File TEMP_DIRECTORY = new File(path);
	    File newDirectory = new File(TEMP_DIRECTORY, "/upload/user/"+usr.getLoginId()+"/image");
	    boolean isExist = CreateFolderUtility.checkAndCreateDirectory(newDirectory);
	    if(!isExist){
			throw new Exception("Not have sufficient privileges to create folder on server");
		}
        String filename=file.getOriginalFilename();  
        long fileSize = file.getSize();
        String filePath=newDirectory+"/"+filename;
        System.out.println(newDirectory+"/"+filename);
        File f = new File(filePath);
        boolean isFileExist = f.exists();
        if(isFileExist) {
        	model.addAttribute("massage", "Image already exist");
        }
        try{  
        byte barr[]=file.getBytes();  
          
        BufferedOutputStream bout=new BufferedOutputStream(  
                 new FileOutputStream(newDirectory+"/"+filename));  
        bout.write(barr); 
        if(!isFileExist) {
        	//throw new Exception("File already exist.");
        	//model.addAttribute("msg", "File already exist");
        	userService.saveImage(filename, fileSize, filePath, usr.getLoginId());
        }
        bout.flush();  
        bout.close();
        model.addAttribute("filePath", filePath);
        model.addAttribute("massage", "image uploaded successfully.");
        }catch(Exception e) {
        	System.out.println(e);
        }

        return "User_KYC";
	}
	
	
	   @GetMapping("/find-seller-by-id/{id}")
	   @ResponseBody
	    public ResponseEntity<Map<String, Boolean>> findSellerById(@PathVariable String id) {
	        boolean exists = userService.existsById(id);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("exists", exists);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	
	   @GetMapping("/My_Team")
		public String getMyTeamDetails(Model model, HttpServletRequest request) {
		  
		    User usr = (User) request.getSession().getAttribute("user");
			List<User> users = userService.getAllMyUsers(usr.getLoginId());
			 model.addAttribute("user", usr);
			 model.addAttribute("userList", users);
			return "My_Teams";
		}
	   
	   @GetMapping("/My_Users")
	   @ResponseBody
		public List<MyChartDetails> getMyTeamUserDetails(HttpServletRequest request, HttpServletResponse httpServletResponse) {
		  
		    User usr = (User) request.getSession().getAttribute("user");
			//List<User> users = ;
			 //model.addAttribute("user", usr);
			 //model.addAttribute("userList", users);
			return userService.getAllMyUsersDetails(usr.getLoginId());
		}
	   @GetMapping("/Projects")
		public String getCompanyProjects(Model model, HttpServletRequest request) {
		  
		    User usr = (User) request.getSession().getAttribute("user");
			//List<User> users = userService.getAllMyUsers(usr.getLoginId());
			 model.addAttribute("user", usr);
			 //model.addAttribute("userList", users);
			return "User_Projects";
		}
	   @GetMapping("/Transport")
		public String getUserTransport(Model model, HttpServletRequest request) {
		  
		    User usr = (User) request.getSession().getAttribute("user");
			//List<User> users = userService.getAllMyUsers(usr.getLoginId());
		    List<TransportDetails> transportDetails = userService.getDetailsByAssociateId(usr.getLoginId());
			 model.addAttribute("transportDetails", transportDetails);
			 model.addAttribute("user", usr);
			 //model.addAttribute("userList", users);
			return "User_Transport_View";
		}
	   
	   @GetMapping("/Create_Transport_Entry")
		public String getUserTransportCreate(Model model, HttpServletRequest request) {
		  
		    User usr = (User) request.getSession().getAttribute("user");
			//List<User> users = userService.getAllMyUsers(usr.getLoginId());
		    //List<TransportDetails> transportDetails = userService.getDetailsByAssociateId(usr.getLoginId());
			// model.addAttribute("transportDetails", transportDetails);
			 model.addAttribute("user", usr);
			 //model.addAttribute("userList", users);
			return "User_Transport";
		}
	   
	   @PostMapping("/saveTransportDetails")
	   @ResponseBody
		public ResponseEntity<String> saveTransportDetails(@RequestBody TransportDetails transportDetails, HttpServletRequest request) {
		   User usr = (User) request.getSession().getAttribute("user");
		   //model.addAttribute("user", usr);
		   TransportDetails transportDetails2 =  userService.saveTransportDetails(transportDetails);
		  // model.addAttribute("msg", "Transport details save successfully.");
		   return ResponseEntity.ok("Transport details save successfully.");
	   }
	
}
