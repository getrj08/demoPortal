package com.rj.demo.ppm.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.rj.demo.ppm.constants.UserRoles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

	@NotEmpty @NotNull
	private String username;
	@NotEmpty @NotNull
	private String password;
	private UserRoles role;
}
