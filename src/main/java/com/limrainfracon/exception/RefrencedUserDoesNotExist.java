package com.limrainfracon.exception;
public class RefrencedUserDoesNotExist extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

	public RefrencedUserDoesNotExist(String message) {
        super(message);
    }
}