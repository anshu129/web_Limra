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

import com.limrainfracon.common.CommisionService;
import com.limrainfracon.model.Plot;
import com.limrainfracon.model.PlotSale;
import com.limrainfracon.model.User;
import com.limrainfracon.service.PlotSaleService;
import com.limrainfracon.service.PlotService;
import com.limrainfracon.service.ProjectService;
import com.limrainfracon.service.UserService;

@Controller
@RequestMapping("/admin")
public class PlotSaleController {
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private PlotSaleService plotSaleService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private PlotService plotService;
    
    @Autowired
    private CommisionService commisionService;
    
   
   

    @GetMapping("/addPlotSale")
    public String showAddPlotSaleForm(Model model, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute("user");
    	model.addAttribute("user", user);
        model.addAttribute("plotSale", new PlotSale());
        model.addAttribute("projects", projectService.getAllProjects());
        return "addPlotSale";
    }
    
	
	@PostMapping("/addPlotSale") 
	public String addPlotSale(@Valid @ModelAttribute PlotSale plotSale,BindingResult bindingResult , Model model, HttpServletRequest request) { 
		User user1 = (User) request.getSession().getAttribute("user");
    	model.addAttribute("user", user1);
		if (bindingResult.hasErrors()) {
		    model.addAttribute("projects", projectService.getAllProjects());
	        return "addPlotSale";
	    }
		if(!plotSale.isSellerIdCheckBox()){
			 model.addAttribute("projects", projectService.getAllProjects());
			  model.addAttribute("message","Seller Id Not Valid");
		     return "addPlotSale";
		}
		
		User user = userService.findByLoginId(plotSale.getSellerId());
		Plot plot =  plotService.getPlotDetailByPlotId(plotSale.getPlot().getPlotId());
		plotSaleService.updateAndSavePlotDetails(plot,plotSale);
		plotSale.setSellerId(user.getLoginId());
		plotSale.setUser(user);
	    commisionService.calculateAndDistributeCommission(user,plotSale.getPlotRate());
	    plotSale.setWallet(user.getWallet());
	    plotSaleService.savePlotSale(plotSale);
	    model.addAttribute("message","Sale Updated Successfully.");
	    model.addAttribute("projects", projectService.getAllProjects());
	    return "addPlotSale";
	     }
}