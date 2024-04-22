package com.tg.cmd_diagnostics_service.externalservice;

import com.tg.cmd_diagnostics_service.models.Bookings;

public interface IClinicService {
	
	/*
	 * Checks if a specific diagnostic service is offered by the clinic
	*/
	
	// Method to check whether a given diagnostic service is offered by the clinic
	public boolean isServiceOfferedByClinic(Bookings bookings);
}
