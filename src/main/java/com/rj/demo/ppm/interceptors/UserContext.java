package com.rj.demo.ppm.interceptors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserContext {
	
	Authentication authentication;

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	
}
