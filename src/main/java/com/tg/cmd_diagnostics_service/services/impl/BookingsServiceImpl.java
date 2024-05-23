package com.tg.cmd_diagnostics_service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tg.cmd_diagnostics_service.exceptions.DiagnosisNotFoundException;
import com.tg.cmd_diagnostics_service.externalservice.ClinicServiceFactory;
import com.tg.cmd_diagnostics_service.externalservice.IClinicService;
import com.tg.cmd_diagnostics_service.externalservice.IPatientService;
import com.tg.cmd_diagnostics_service.externalservice.PatientServiceFactory;
import com.tg.cmd_diagnostics_service.models.Bookings;
import com.tg.cmd_diagnostics_service.repositories.BookingsRepository;
import com.tg.cmd_diagnostics_service.services.BookingsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingsServiceImpl implements BookingsService  {
	
	// Injects the BookingsRepository dependency for interacting with the database
	@Autowired
	public BookingsRepository bookingsRepository;

	@Override
	public Bookings bookService(Bookings bookings) {

		boolean isServiceOfferedByClinic = false;
		boolean isPatientActive = false;
		
		// Checks if the service is offered by the clinic
		if (checkForServiceAvailabilityInClinic(bookings)) {
			isServiceOfferedByClinic = true;
		} else {
			log.info("Failed");
		}

		if (getPatientStatusFromPatientApi(bookings)) {
			isPatientActive = true;
		} else {
			log.info("Failed");
		}
		
		// If both conditions are true, save the booking
		if (isServiceOfferedByClinic && isPatientActive) {

			return this.bookingsRepository.save(bookings);
		}else {
			throw new DiagnosisNotFoundException("Not Found");
		}
	}

	@Override
	public boolean cancelService(String bookingId) {

		log.info("Attempting to delete booking with ID: {}", bookingId);

		// Check if the booking exists
		if (bookingsRepository.findById(bookingId).isPresent()) {
			bookingsRepository.deleteById(bookingId);
			log.info("Successfully deleted booking with ID: {}", bookingId);
			return true;
		}
		 log.error("Failed to delete booking with ID: {}", bookingId);
		return false;
	}

	@Override
	public Bookings viewBookingsById(String bookingId) {
		
		log.info("Fetching diagnosis by ID: {}", bookingId);
		return this.bookingsRepository.findById(bookingId).orElse(null);

	}

	private boolean getPatientStatusFromPatientApi(Bookings bookings) {
		
		// Checks if the patient is active using a mock service
		IPatientService service = PatientServiceFactory.create("Mock");
		return service.getPatientStatusFromPatientApi(bookings);
	}

	private boolean checkForServiceAvailabilityInClinic(Bookings bookings) {
		
		// Checks if the service is offered by the clinic using a mock service
		IClinicService service = ClinicServiceFactory.create("Mock");
		return service.isServiceOfferedByClinic(bookings);
	}
}