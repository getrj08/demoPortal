package com.rj.demo.ppm.iservices;

import java.util.List;

import com.rj.demo.ppm.entities.Project;
import com.rj.demo.ppm.models.ProjectModel;

public interface IProjectService {
	
	public Project getProject(String id);
	
	public List<Project> getProjects();

	public Project createProject(ProjectModel project);
	
	public Project updateProject(String projectIdentifier, ProjectModel project);
	
	public String deleteProject(String id);

}
