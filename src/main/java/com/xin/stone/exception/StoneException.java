package com.xin.stone.exception;

import java.io.IOException;

import com.xin.stone.ast.ASTree;

public class StoneException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1838178853409798101L;

	public StoneException(String message) {
		super(message);
	}
	
	public StoneException(String message, ASTree ast) {
		super(message + "\t" + ast.getLocation());
	}

}
