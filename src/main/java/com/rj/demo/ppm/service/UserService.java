package com.rj.demo.ppm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.rj.demo.ppm.custom.exceptions.InvalidLoginException;
import com.rj.demo.ppm.entities.User;
import com.rj.demo.ppm.irepositories.IUserRepository;
import com.rj.demo.ppm.iservices.IUserService;
import com.rj.demo.ppm.models.UserModel;
import com.rj.demo.ppm.utils.JwtTokenUtil;

@Service
public class UserService implements IUserService{

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	JwtTokenUtil jwtUtil;
	
	@Override
	public User registerUser(UserModel usermodel) {
		User user = User.builder()
				.username(usermodel.getUsername())
				.password(usermodel.getPassword().toCharArray()).build();
		SimpleGrantedAuthority userRole = new SimpleGrantedAuthority(usermodel.getRole().name());
		user.setRole(userRole);
		return userRepository.save(user);
	}

	@Override
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public String authenticateUser(UserModel usermodel) {
		Optional<User> validUser = userRepository.findById(usermodel.getUsername());
		if(validUser.isPresent() && String.valueOf(validUser.get().getPassword()).equals(usermodel.getPassword())) {
			return jwtUtil.generateToken(validUser.get());
		} else {
			throw new InvalidLoginException("Username/Password is invalid");
		}
	}

}
