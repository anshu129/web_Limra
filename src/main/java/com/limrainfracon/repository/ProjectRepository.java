package com.limrainfracon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.limrainfracon.model.Project;

@Repository
public interface ProjectRepository  extends JpaRepository<Project, String>{

	Project findByProjectName(String projectName);

}
