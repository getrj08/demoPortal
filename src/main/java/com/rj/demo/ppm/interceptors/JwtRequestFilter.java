package com.rj.demo.ppm.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rj.demo.ppm.custom.exceptions.InvalidLoginException;
import com.rj.demo.ppm.entities.User;
import com.rj.demo.ppm.iservices.IUserService;
import com.rj.demo.ppm.utils.JwtTokenUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	UserContext usrContext;
	
	private String[] skipUrls = {"/user/register" , "/user/authenticate"};
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authToken = request.getHeader("Authorization");
		if(Arrays.asList(skipUrls).contains(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String username = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		
		try {
			username = jwtTokenUtil.getUsernameFromToken(authToken);
			if(username != null && securityContext.getAuthentication() == null) {
				User userDetails = userService.getUser(username);
				if(!Boolean.TRUE.equals(jwtTokenUtil.validateToken(authToken, userDetails))) {
					throw new InvalidLoginException("Invalid jwt token given");
				}
				List<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(userDetails.getRole());
				UsernamePasswordAuthenticationToken usrpwdAuthToken = 
						new UsernamePasswordAuthenticationToken(userDetails.getUsername(), authToken, authorities);
				usrpwdAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				securityContext.setAuthentication(usrpwdAuthToken);
				usrContext.setAuthentication(securityContext.getAuthentication());
				filterChain.doFilter(request, response);
			} else {
				throw new InvalidLoginException("Invalid username given");
			}
		} catch(Exception e) {
			throw new InvalidLoginException(e.getMessage());
		}
		
	}

}
