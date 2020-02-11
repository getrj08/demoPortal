package com.rj.demo.ppm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.rj.demo.ppm.configs.TestSecurityContextConfig;
import com.rj.demo.ppm.controller.ProjectController;
import com.rj.demo.ppm.entities.Project;
import com.rj.demo.ppm.interceptors.UserContext;
import com.rj.demo.ppm.models.ProjectModel;

@SpringBootTest(classes=TestSecurityContextConfig.class)
class PpmApplicationTests {

	@Autowired
	ProjectController projectController;
	
	@Autowired
	UserContext ctx;
	
	@BeforeEach
	public void setSecurityContext() {
		ctx.setAuthentication(getAuth());
	}
	
	public Authentication getAuth() {
		Authentication auth = new UsernamePasswordAuthenticationToken("admin", "admin",
				Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
		return auth;
	}
	 
	@Test
	public void createProjectTest() {
		ProjectModel model = ProjectModel.builder()
				.projectName("p1xx")
				.projectDescription("Sample p")
				.projectIdentifier("p1xx").build();
		ResponseEntity<Project> response = projectController.createProject(model);
		assert response.getStatusCode().equals(HttpStatus.CREATED);
		assertNotNull(response.getBody());
		assertEquals("p1xx",response.getBody().getProjectIdentifier());
		assertEquals("admin",response.getBody().getCreatedBy());
		ResponseEntity<String> deletedResponse = projectController.deleteProject("p1xx");
		assertEquals(HttpStatus.NO_CONTENT, deletedResponse.getStatusCode());
	}
	
	@Test
	public void getProjectTest() {
		ProjectModel model = ProjectModel.builder()
				.projectName("p1xx")
				.projectDescription("Sample p")
				.projectIdentifier("p1xx").build();
		ResponseEntity<Project> response = projectController.createProject(model);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("p1xx",response.getBody().getProjectIdentifier());
		assertEquals("admin",response.getBody().getCreatedBy());
		ResponseEntity<Project> getResponse = projectController.getProject("p1xx");
		assertEquals(HttpStatus.OK, getResponse.getStatusCode());
		assertEquals("p1xx",getResponse.getBody().getProjectIdentifier());
		assertEquals("admin",getResponse.getBody().getCreatedBy());
		ResponseEntity<String> deletedResponse = projectController.deleteProject("p1xx");
		assertEquals(HttpStatus.NO_CONTENT, deletedResponse.getStatusCode());
	}
	
	
	
	@Test
	// until embedded mongo is done this will not work
	public void getProjectsTest() {
		ResponseEntity<String> deletedResponse = null;
		ProjectModel model1 = ProjectModel.builder()
				.projectName("p1xx")
				.projectDescription("Sample p1")
				.projectIdentifier("p1xx").build();
		ProjectModel model2 = ProjectModel.builder()
				.projectName("p2xx")
				.projectDescription("Sample p2")
				.projectIdentifier("p2xx").build();
		ProjectModel model3 = ProjectModel.builder()
				.projectName("p3xx")
				.projectDescription("Sample p3")
				.projectIdentifier("p3xx").build();
		projectController.createProject(model1);
		projectController.createProject(model2);
		projectController.createProject(model3);
		ResponseEntity<List<Project>> getResponse = projectController.getProjects();
		assertEquals(HttpStatus.OK, getResponse.getStatusCode());
		assertEquals(3, getResponse.getBody().size());
		deletedResponse = projectController.deleteProject("p1xx");
		assertEquals(HttpStatus.NO_CONTENT, deletedResponse.getStatusCode());
		deletedResponse = projectController.deleteProject("p2xx");
		assertEquals(HttpStatus.NO_CONTENT, deletedResponse.getStatusCode());
		deletedResponse = projectController.deleteProject("p3xx");
		assertEquals(HttpStatus.NO_CONTENT, deletedResponse.getStatusCode());
	}
	
	@Test
	public void getUpdateTest() {
		ProjectModel model = ProjectModel.builder()
				.projectName("p1xx")
				.projectDescription("Sample p")
				.projectIdentifier("p1xx").build();
		ResponseEntity<Project> response = projectController.createProject(model);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		model.setProjectName("p2xx");
		ResponseEntity<Project> updateResponse = projectController.updateProject("p1xx", model);
		assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
		assertEquals("p2xx",updateResponse.getBody().getProjectName());
		assertEquals("p1xx",updateResponse.getBody().getProjectIdentifier());
		assertEquals("admin",updateResponse.getBody().getLastModifiedBy());
		ResponseEntity<String> deletedResponse = projectController.deleteProject("p1xx");
		assertEquals(HttpStatus.NO_CONTENT, deletedResponse.getStatusCode());
		
	}
	
}
