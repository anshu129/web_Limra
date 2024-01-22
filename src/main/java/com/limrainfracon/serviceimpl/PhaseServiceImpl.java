package com.limrainfracon.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limrainfracon.model.Phase;
import com.limrainfracon.model.Project;
import com.limrainfracon.repository.PhaseRepository;
import com.limrainfracon.repository.ProjectRepository;
import com.limrainfracon.service.PhaseService;

@Service
public class PhaseServiceImpl implements PhaseService{
	@Autowired
    private PhaseRepository phaseRepository;
	@Autowired
    private ProjectRepository projectRepository;

   

    public Phase addPhaseToProject(String projectId, Phase phase) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        phase.setProject(project);
        return phaseRepository.save(phase);
    }



	@Override
	public List<Phase> getPhasesByProjectId(String projectId) {
		return phaseRepository.findPhasesByProjectId(projectId);
	}



	@Override
	public Phase findPhaseByPhaseName(String phaseName) {
		
		return phaseRepository.findByPhaseName(phaseName);
	}
}