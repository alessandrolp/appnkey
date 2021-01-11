package br.com.nkey.exception;

import org.springframework.http.HttpStatus;

public class ApiRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus httpStatus;

	public ApiRuntimeException(HttpStatus httpStatus, String message) {
		this(httpStatus, message, null);
	}

	public ApiRuntimeException(HttpStatus httpStatus, String message, Throwable throwable) {
		super(message, throwable);
		this.httpStatus = httpStatus;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
