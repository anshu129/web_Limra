package com.limrainfracon.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limrainfracon.model.Project;
import com.limrainfracon.repository.ProjectRepository;
import com.limrainfracon.service.ProjectService;

@Service

public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public void saveProject(Project project) {
		projectRepository.save(project);
	}

	@Override
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	@Override
	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	@Override
	public Project findProjectByProjectName(String projectName) {
		
		return projectRepository.findByProjectName(projectName);
	}

}
