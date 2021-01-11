package br.com.nkey.exception.handler;

import lombok.Value;

@Value
public class ValidationError {
	
	private String fieldName;
	private String error;

}
