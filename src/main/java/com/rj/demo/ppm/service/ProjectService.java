package com.rj.demo.ppm.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.rj.demo.ppm.constants.UserRoles;
import com.rj.demo.ppm.entities.Project;
import com.rj.demo.ppm.interceptors.UserContext;
import com.rj.demo.ppm.irepositories.IProjectRepository;
import com.rj.demo.ppm.iservices.IProjectService;
import com.rj.demo.ppm.models.ProjectModel;

@Service
public class ProjectService implements IProjectService{

	@Autowired
	IProjectRepository projectRepository;
	
	@Autowired
	UserContext usrCtx;
	
	@Override
	public Project getProject(String projectIdentifier) {
		return projectRepository.findByProjectIdentifier(projectIdentifier);
	}

	@Override
	public Project createProject(ProjectModel project) {
		Project projectEntity = Project.builder()
				.id(UUID.randomUUID().toString())
				.createdAt(new Date())
				.projectDescription(project.getProjectDescription())
				.projectName(project.getProjectName())
				.projectIdentifier(project.getProjectIdentifier())
				.createdBy(usrCtx.getAuthentication().getName())
				.build();
		
			return projectRepository.save(projectEntity); 
		
	}

	@Override
	public Project updateProject(String projectIdentifier, ProjectModel project) {
		Project projectData = projectRepository.findByProjectIdentifier(projectIdentifier);
		projectData.setProjectDescription(project.getProjectDescription());
		projectData.setProjectName(project.getProjectName());
		if(!projectIdentifier.equals(project.getProjectIdentifier())) {
			projectData.setProjectIdentifier(project.getProjectIdentifier());
		}
		projectData.setLastModifiedBy(usrCtx.getAuthentication().getName());
		projectData.setUpdatedAt(new Date());
		
		return projectRepository.save(projectData);
	}

	@Override
	public List<Project> getProjects() {
		String username = usrCtx.getAuthentication().getName();
		Collection<? extends GrantedAuthority> authorities = usrCtx.getAuthentication().getAuthorities();
		List<String> rolesList = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		if(rolesList.contains(UserRoles.ADMIN.name())) {
			return projectRepository.findAll();
		} else {
			return projectRepository.findProjectByUsername(username, Sort.by(Sort.Direction.ASC,"createdAt"));
		}
		
	}

	@Override
	public String deleteProject(String id) {
		projectRepository.deleteByProjectIdentifier(id);
		return id;
	}

}
