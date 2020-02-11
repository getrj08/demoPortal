package com.rj.demo.ppm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rj.demo.ppm.entities.Project;
import com.rj.demo.ppm.iservices.IProjectService;
import com.rj.demo.ppm.models.ProjectModel;

@Controller
@RequestMapping(value="/ppm")
public class ProjectController {
	
	@Autowired
	IProjectService projectService;

	@GetMapping(value="/project/{id}")
	public ResponseEntity<Project> getProject(@PathVariable("id") String id) {
		Project project = projectService.getProject(id);
		return ResponseEntity.ok().body(project);
	}
	
	@GetMapping(value="/projects")
	public ResponseEntity<List<Project>> getProjects() {
		return ResponseEntity.ok().body(projectService.getProjects());
	}
	
	@PutMapping(value="/project")
	public ResponseEntity<Project> createProject(@RequestBody ProjectModel project) {
		return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(project));
	}
	
	@PostMapping(value="/project/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable("id") String id, @RequestBody ProjectModel project) {
		return ResponseEntity.status(HttpStatus.OK).body(projectService.updateProject(id, project));
	}
	
	@DeleteMapping(value="/project/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(projectService.deleteProject(id));
		
	}
	
	
}
