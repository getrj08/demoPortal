package com.rj.demo.ppm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rj.demo.ppm.entities.User;
import com.rj.demo.ppm.iservices.IUserService;
import com.rj.demo.ppm.models.UserModel;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	IUserService userService;

	@GetMapping(value="/get/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		return ResponseEntity.ok().body(userService.getUser(username));
	}
	
	@PostMapping(value="/authenticate")
	public ResponseEntity<String> authenticateUser(@RequestBody UserModel user) {
		return ResponseEntity.ok().body(userService.authenticateUser(user));
	}
	
	@PutMapping(value="/register")
	public ResponseEntity<User> createProject(@RequestBody UserModel user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
	}

}
