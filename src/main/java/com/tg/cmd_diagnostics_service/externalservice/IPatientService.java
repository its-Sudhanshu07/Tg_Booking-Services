package com.tg.cmd_diagnostics_service.externalservice;

import com.tg.cmd_diagnostics_service.models.Bookings;

public interface IPatientService {
	
	// Method to check the patient status based on external information or an API call
	public boolean getPatientStatusFromPatientApi(Bookings bookings); 
	
}
