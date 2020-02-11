package com.rj.demo.ppm.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document(collection = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	private String username;
	private char[] password;
	private SimpleGrantedAuthority role;
	
}
