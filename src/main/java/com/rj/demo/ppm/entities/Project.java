package com.rj.demo.ppm.entities;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document(collection = "project")
@AllArgsConstructor
@NoArgsConstructor
public class Project {

	@Id
	private String id;
	@NotNull @NotEmpty
	private String projectName;
	@NotEmpty
	private String projectDescription;
	@NotNull @Indexed(unique=true)
	private String projectIdentifier;
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String lastModifiedBy;
	
}
