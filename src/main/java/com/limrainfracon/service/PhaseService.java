package com.limrainfracon.service;

import java.util.List;

import com.limrainfracon.model.Phase;

public interface PhaseService {
	Phase addPhaseToProject(String projectId, Phase phase);

	List<Phase> getPhasesByProjectId(String projectId);

	Phase findPhaseByPhaseName(String phaseName);
}
