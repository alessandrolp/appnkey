package br.com.nkey.exception;

import org.springframework.http.HttpStatus;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessRuntimeException extends ApiRuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BusinessRuntimeException(String message) {
		this(message, null);
	}
	
	public BusinessRuntimeException(String message, Throwable throwable) {
		super(HttpStatus.BAD_REQUEST, message, throwable);
	}

}
