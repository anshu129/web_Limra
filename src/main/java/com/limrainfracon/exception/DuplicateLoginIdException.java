package com.limrainfracon.exception;

public class DuplicateLoginIdException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DuplicateLoginIdException(String message) {
        super(message);
    }

}
