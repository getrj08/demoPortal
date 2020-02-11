package com.rj.demo.ppm.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectModel {

	@NotEmpty @NotNull
	private String projectName;
	@NotEmpty
	private String projectDescription;
	@NotNull
	private String projectIdentifier;
}
