package com.tg.cmd_diagnostics_service.externalservice;

import com.tg.cmd_diagnostics_service.exceptions.BadChoiceException;

public class PatientServiceFactory {
	
	public static IPatientService create(String choice) {
		
		// Declaration of the variable that will hold the appropriate service
		IPatientService service = null;
		// Check if the choice is "Mock" to return a mock implementation of the service
		if (choice.equalsIgnoreCase("Mock")) {
			service = new PatientServiceMockImpl();
		} 
		// Check if the choice is "Service" to return the real implementation
		else if (choice.equalsIgnoreCase("Service")) {
			service = new PatientServiceImpl();
		} else {
			throw new BadChoiceException("Bad choice choose between service and ");
		}
		
		return service;
	}
}
