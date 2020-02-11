package com.rj.demo.ppm.irepositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.rj.demo.ppm.entities.Project;

@Repository
public interface IProjectRepository extends MongoRepository<Project, String> {

	Project findByProjectIdentifier(String projectIdentifier);
	
	@Query("{'createdBy' : ?0}")
	List<Project> findProjectByUsername(String username,Sort sort);
	
	void deleteByProjectIdentifier(String projectIdeString);
}
