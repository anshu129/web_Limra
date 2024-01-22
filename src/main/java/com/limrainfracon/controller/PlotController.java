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

import com.limrainfracon.enums.PlotStatus;
import com.limrainfracon.enums.PlotType;
import com.limrainfracon.model.Phase;
import com.limrainfracon.model.Plot;
import com.limrainfracon.model.User;
import com.limrainfracon.service.PlotService;
import com.limrainfracon.service.ProjectService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class PlotController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private PlotService plotService;

    @GetMapping("/addPlot")
    public String addPlotForm(Model model, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("plot", new Plot());
        model.addAttribute("user", user);
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("plotStatuses", PlotStatus.values());
        model.addAttribute("plotTypes", PlotType.values());
        model.addAttribute("phase", new Phase());
        return "add_plot";
    }

    @PostMapping("/addPlot")
    public String savePlot(@Valid @ModelAttribute("plot") Plot plot,BindingResult bindingResult, Model model, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("user", user);
    	if (bindingResult.hasErrors()) {
	        return "add_plot";
	    }
    	Plot existingPlot =  plotService.findPlotByProjectIdPhaseIdAndPlotNumber(plot.getProject().getProjectId(),plot.getPhase().getPhaseId(),plot.getPlotNumber());
    	if(existingPlot != null) {
    		model.addAttribute("message","Plot With This Name Already Exist.");
    	}else {
    		plotService.savePlot(plot);
    	    model.addAttribute("message","Plot Added Successfully");
    	}
    	model.addAttribute("plot", new Plot());
    	//model.addAttribute("user", user);
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("plotStatuses", PlotStatus.values());
        model.addAttribute("plotTypes", PlotType.values());
        model.addAttribute("phase", new Phase());
        return "add_plot";
    }
    
	@GetMapping("/getPlots")
	@ResponseBody
	public List<Plot> getPlotsByPhaseId(@RequestParam("phaseId") String phaseId) {
	return plotService.getPlotsByPhaseId(phaseId);
	}
	
	
	@GetMapping("/getPlotDetails")
	@ResponseBody
	public Plot getPlotDetailByPlotId(@RequestParam("plotId") String plotId) {
		return plotService.getPlotDetailByPlotId(plotId);
		
	}
}
