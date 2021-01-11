package br.com.nkey.exception.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.nkey.exception.ApiRuntimeException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ApiErrorDetails {
	
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	@JsonInclude(value = Include.NON_EMPTY)
	private List<ValidationError> validationErrors = new ArrayList<>();
	private String path;

	public static ApiErrorDetails of(ApiRuntimeException e, String path) {
		return ApiErrorDetails.builder()
				.status(e.getHttpStatus().value())
				.error(e.getHttpStatus().name())
				.message(e.getMessage())
				.path(path)
				.build();
	}
	
	public static ApiErrorDetails of(MethodArgumentNotValidException ex, String path) {
		List<ValidationError> validationErrors = ex.getFieldErrors().stream()
				.map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
				.collect(Collectors.toList());
		
		return ApiErrorDetails.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.error(HttpStatus.BAD_REQUEST.name())
				.message(ex.getLocalizedMessage())
				.path(path)
				.validationErrors(validationErrors)
				.build();
	}
	
	@Builder
	private ApiErrorDetails(int status, String error, String message, String path, List<ValidationError> validationErrors) {
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
		this.validationErrors = validationErrors;
		this.timestamp = LocalDateTime.now();
	}
	
}
