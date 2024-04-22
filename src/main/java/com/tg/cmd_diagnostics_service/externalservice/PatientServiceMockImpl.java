package com.tg.cmd_diagnostics_service.externalservice;


import com.tg.cmd_diagnostics_service.models.Bookings;
import com.tg.cmd_diagnostics_service.models.Patient;

public class PatientServiceMockImpl implements IPatientService{
	
	private Patient patient =new Patient();
	
	// Mock method to simulate getting the patient status from an API
	@Override
	public boolean getPatientStatusFromPatientApi(Bookings bookings) {
		
		// Set the patient status to active (mock data)
		patient.setActive(true);
		// Return the active status of the patient
		return patient.isActive();
	}

}
