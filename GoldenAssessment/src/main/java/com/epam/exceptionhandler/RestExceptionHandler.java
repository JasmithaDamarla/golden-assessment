package com.epam.exceptionhandler;

import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.epam.exceptions.AssociatesException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
			WebRequest request) {
		log.error("handleMethodArgumentNotValidException was raised " + exception.getMessage());
		return new ExceptionResponse(
				new Date().toString(), HttpStatus.NOT_ACCEPTABLE.name(), exception.getAllErrors().stream()
						.map(error -> error.getDefaultMessage()).reduce("", (a, b) -> a + "\n" + b),
				request.getDescription(false));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception,
			WebRequest request) {
		log.info("handleMethodArgumentTypeMismatchException was raised " + exception.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.name(), exception.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(AssociatesException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleAssociatesException(AssociatesException associateException, WebRequest request) {
		log.error("associates exception was raised " + associateException.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.name(),
				associateException.getMessage(), request.getDescription(false));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse handleDataIntegrityViolationException(
			DataIntegrityViolationException dataIntegrityViolationException, WebRequest request) {
		log.error("dataIntegrityViolationException was raised " + dataIntegrityViolationException.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
				dataIntegrityViolationException.getMessage(), request.getDescription(false));
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse handleRuntimeException(RuntimeException runtime, WebRequest request) {
		log.error("runtimeException has raised " + runtime.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
				runtime.getMessage(), request.getDescription(false));
	}
}