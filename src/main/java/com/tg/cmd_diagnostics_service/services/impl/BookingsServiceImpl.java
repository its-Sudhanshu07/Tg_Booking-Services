package com.tg.cmd_diagnostics_service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

	@Autowired
	public BookingsRepository bookingsRepository;

	@Override
	public Bookings bookService(Bookings bookings) {

		boolean isServiceOfferedByClinic = false;
		boolean isPatientActive = false;

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

		IPatientService service = PatientServiceFactory.create("Mock");
		return service.getPatientStatusFromPatientApi(bookings);
	}

	private boolean checkForServiceAvailabilityInClinic(Bookings bookings) {

		IClinicService service = ClinicServiceFactory.create("Mock");
		return service.isServiceOfferedByClinic(bookings);
	}
}