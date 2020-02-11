package com.rj.demo.ppm.iservices;

import com.rj.demo.ppm.entities.User;
import com.rj.demo.ppm.models.UserModel;

public interface IUserService {
	
	User registerUser(UserModel user);
	User getUser(String username);
	String authenticateUser(UserModel user);
	
	
}
