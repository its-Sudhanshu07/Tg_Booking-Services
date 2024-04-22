package com.tg.cmd_diagnostics_service.externalservice;

import com.tg.cmd_diagnostics_service.exceptions.BadChoiceException;

//Factory class to create instances of different implementations of IClinicService
public class ClinicServiceFactory {

	public static IClinicService create(String choice) {

		IClinicService service = null;
		
		// If the choice is "Mock", return an instance of ClinicServiceMockImpl
		if (choice.equalsIgnoreCase("Mock")) {
			service = new ClinicServiceMockImpl();
			
		// If the choice is "Service", return an instance of ClinicServiceImpl
		} else if (choice.equalsIgnoreCase("Service")) {
			service = new ClinicServiceImpl();
		// If the choice is neither "Mock" nor "Service", throw an exception
		} else {
			throw new BadChoiceException("Bad choice choose between service and ");
		}
		
		return service;
	}
}
