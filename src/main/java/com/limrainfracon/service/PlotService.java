package com.limrainfracon.service;

import java.util.List;

import com.limrainfracon.model.Plot;

public interface PlotService {

	void savePlot(Plot plot);

	List<Plot> getPlotsByProjectId(Long projectId);

	List<Plot> getPlotsByPhaseId(String phaseId);

	Plot getPlotDetailByPlotId(String plotId);

	Plot findPlotByProjectIdPhaseIdAndPlotNumber(String projectId, String phaseId,String plotNumber);

	

	//Plot fndPlotById(Long l);

}
