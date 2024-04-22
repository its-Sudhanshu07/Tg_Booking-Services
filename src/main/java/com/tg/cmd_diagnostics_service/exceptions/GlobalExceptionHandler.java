package com.tg.cmd_diagnostics_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tg.cmd_diagnostics_service.dtos.ResponseWrapper;

//Indicates that this class provides global exception handling for controllers
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseWrapper> handleRuntimeException(RuntimeException exception){
		
		// Method to handle RuntimeException and return a response with HTTP 500 (Internal Server Error)
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseWrapper(exception.getMessage()));
	}
	
	@ExceptionHandler(DiagnosisNotFoundException.class)
	public ResponseEntity<ResponseWrapper> handleDiagnosisNotFoundException
	(DiagnosisNotFoundException exception){
		
		// Method to handle a custom exception when a diagnosis is not found
		// Returns a response with HTTP 500 (Internal Server Error) and an appropriate message
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // Sets HTTP status to 500
				.body(new ResponseWrapper(exception.getMessage()));
	}
	
	
	@ExceptionHandler(BadChoiceException.class)
	public ResponseEntity<ResponseWrapper> handleBadChoiceException
	(BadChoiceException exception){
		
		// Method to handle a custom exception when Bad Choice is made
		// Returns a response with HTTP 500 (Internal Server Error) and an appropriate message
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // Sets HTTP status to 500
				.body(new ResponseWrapper(exception.getMessage()));
	}
}
