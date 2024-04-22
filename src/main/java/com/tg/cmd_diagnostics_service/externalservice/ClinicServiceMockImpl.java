package com.tg.cmd_diagnostics_service.externalservice;

import java.util.ArrayList;
import java.util.List;

import com.tg.cmd_diagnostics_service.models.Bookings;
import com.tg.cmd_diagnostics_service.models.Clinic;
import com.tg.cmd_diagnostics_service.models.ServicesOffered;

public class ClinicServiceMockImpl implements IClinicService{
	
	Clinic clinic=new Clinic();
	@Override
	public boolean isServiceOfferedByClinic(Bookings bookings) {
		
		// Initialize a flag to indicate if the service is offered
		boolean isServiceOffered=false;
		// Create a list of services offered by the clinic
		List<ServicesOffered>services=new ArrayList<>();
		services.add(ServicesOffered.BloodTest);
		services.add(ServicesOffered.CovidTest);
		services.add(ServicesOffered.CTScan);
		clinic.setServicesOffered(services);
		
		// Iterate over the requested services in the Diagnostics object
		for(ServicesOffered serviceRequested: bookings.getServices()) {
			// Check if each requested service is among the clinic's offered services
			for(ServicesOffered service:services) {
				// If a match is found, set the flag to true and break out of the loop
				if(serviceRequested.toString().equalsIgnoreCase(service.toString())) {
					isServiceOffered=true;
					break;
				}else {
					isServiceOffered=false;
				}
			}
		}
		return isServiceOffered;
	}

}
