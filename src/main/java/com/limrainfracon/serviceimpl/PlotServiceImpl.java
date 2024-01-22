package com.limrainfracon.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limrainfracon.model.Plot;
import com.limrainfracon.repository.PlotRepository;
import com.limrainfracon.service.PlotService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PlotServiceImpl implements PlotService {
	
	@Autowired
	private PlotRepository plotRepository;

	@Override
	public void savePlot(Plot plot) {
		plotRepository.save(plot);
	}

	@Override
	public List<Plot> getPlotsByProjectId(Long projectId) {
		log.info(plotRepository.findPlotsByProjectId(projectId).toString());
		return plotRepository.findPlotsByProjectId(projectId);
	}

	@Override
	public List<Plot> getPlotsByPhaseId(String phaseId) {
		return plotRepository.findPlotsByPhaseId(phaseId);
		
	}

	@Override
	public Plot getPlotDetailByPlotId(String plotId) {
		
		return plotRepository.findByPlotId(plotId);
	}

	@Override
	public Plot findPlotByProjectIdPhaseIdAndPlotNumber(String projectId, String phaseId, String plotNumber) {
		Optional<Plot> ploOptional = plotRepository.findByProjectIdPhaseIdAndPlotNumber(projectId,phaseId,plotNumber);
		if(ploOptional.isPresent()) {
			return 	ploOptional.get();
		}
		return null;
	}

	/*
	 * @Override public Plot fndPlotById(String plotNumber) { return
	 * plotRepository.findById(plotNumber).orElseThrow(()-> new
	 * IllegalArgumentException()); }
	 */

}
