package com.rj.demo.ppm.irepositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rj.demo.ppm.entities.User;

public interface IUserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);
}
