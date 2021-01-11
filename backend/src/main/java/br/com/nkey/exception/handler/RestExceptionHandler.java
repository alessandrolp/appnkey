package br.com.nkey.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.nkey.exception.ApiRuntimeException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ApiRuntimeException.class)
	public final ResponseEntity<ApiErrorDetails> handlerApiRuntimeException(ApiRuntimeException e, HttpServletRequest request) {
		return new ResponseEntity<>(ApiErrorDetails.of(e, request.getRequestURI()), e.getHttpStatus());
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final String path = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
		return new ResponseEntity<>(ApiErrorDetails.of(ex, path), HttpStatus.BAD_REQUEST);
	}

}
