package com.example.ec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchElementException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchElementException() {
		super();
	}

	public NoSuchElementException(String message) {
		super(message);
	}

	public NoSuchElementException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchElementException(Throwable cause) {
		super(cause);
	}
}
