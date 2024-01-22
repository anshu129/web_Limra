package com.limrainfracon.service;

import java.util.List;

import com.limrainfracon.model.Project;

public interface ProjectService {

	void saveProject(Project project);

	List<Project> getAllProjects();

	List<Project> findAll();

	Project findProjectByProjectName(String projectName);

}
