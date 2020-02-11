package com.rj.demo.ppm.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mongodb.MongoWriteException;
import com.rj.demo.ppm.custom.exceptions.InvalidLoginException;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MongoWriteException.class)
	protected ResponseEntity<String> handleEntityNotFound(MongoWriteException ex) {
		// com.mongodb.MongoWriteException: E11000 duplicate key error collection:
		// ppm.project index: projectIdentifier dup key: { projectIdentifier: "abc1" }
		String msg = "Error occured while saving data";
		switch (ex.getCode()) {
		case 11000:
			String message = ex.getMessage().substring(ex.getMessage().indexOf("dup key:"));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
		}
		return ResponseEntity.badRequest().body(msg);
	}
	
	@ExceptionHandler(InvalidLoginException.class)
	protected ResponseEntity<String> handleLoginException(InvalidLoginException ex) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
	}
}
