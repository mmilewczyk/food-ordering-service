package com.food.ordering.system.application.handler;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = {Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorDTO handleException(Exception exception) {
		log.error(exception.getMessage(), exception);
		return ErrorDTO.builder()
				.code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
				.message("Unexpected error!")
				.build();
	}

	@ResponseBody
	@ExceptionHandler(value = {ValidationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleException(ValidationException validationException) {
		if (validationException instanceof ConstraintViolationException constraintViolationException) {
			String violations = extractViolationsFromException(constraintViolationException);
			log.error(violations, constraintViolationException);
			return ErrorDTO.builder()
					.code(HttpStatus.BAD_REQUEST.getReasonPhrase())
					.message(violations)
					.build();
		}
		String exceptionMessage = validationException.getMessage();
		log.error(exceptionMessage, validationException);
		return ErrorDTO.builder()
				.code(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.message(exceptionMessage)
				.build();
	}

	private String extractViolationsFromException(ConstraintViolationException constraintViolationException) {
		return constraintViolationException.getConstraintViolations()
				.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.joining("--"));
	}
}
