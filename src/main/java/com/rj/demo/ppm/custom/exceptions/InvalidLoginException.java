package com.rj.demo.ppm.custom.exceptions;

public class InvalidLoginException extends RuntimeException {

	
	private static final long serialVersionUID = 6482859398277266752L;

	public InvalidLoginException(String msg) {
		super(msg);
	}
}
