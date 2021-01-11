package br.com.nkey.exception;

import org.springframework.http.HttpStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends ApiRuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DocumentNotFoundException(String message) {
		this(message, null);
	}
	
	public DocumentNotFoundException(String message, Throwable throwable) {
		super(HttpStatus.NOT_FOUND, message, throwable);
	}

}
