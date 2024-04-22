package com.tg.cmd_diagnostics_service.exceptions;

public class DiagnosisNotFoundException extends RuntimeException{
	
	public DiagnosisNotFoundException(String message) {
		
		super(message);
	}
}
