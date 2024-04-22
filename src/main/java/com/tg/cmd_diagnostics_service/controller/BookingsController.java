package com.tg.cmd_diagnostics_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tg.cmd_diagnostics_service.dtos.ResponseWrapper;
import com.tg.cmd_diagnostics_service.models.Bookings;
import com.tg.cmd_diagnostics_service.services.BookingsService;



@RestController
@RequestMapping("/bookings")// Base URL mapping for all endpoints in this controller
public class BookingsController {
	
	@Autowired // Injecting DiagnosticService dependency
	private BookingsService bookingsService;
	
	@PostMapping("/bookService") // Maps HTTP POST requests to this method
	@CrossOrigin(allowedHeaders = "*",origins = "*", 
	methods=RequestMethod.POST)
	public ResponseEntity<ResponseWrapper> bookService(@RequestBody Bookings bookings){
		
		// Endpoint to book a new diagnostic service
		// Receives data in JSON format in the request body and creates a new service

		Bookings addedBookings= bookingsService.bookService(bookings);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(addedBookings));
	}
	
	@DeleteMapping("/{bookingId}")
	@CrossOrigin(allowedHeaders = "*",origins = "*", 
   	methods=RequestMethod.DELETE)
	public ResponseEntity<ResponseWrapper> cancelService(@PathVariable("bookingId") String bookingId){
		
		// Endpoint to cancel a diagnostic service based on its ID
		// Uses a path variable to specify which service to delete
		
		boolean deleted =bookingsService.cancelService(bookingId);
		
		if (deleted)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper("Booking with ID " + bookingId + " deleted"));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper("Booking with ID " + bookingId + " not found"));
    }
	
	@GetMapping("/{bookingId}")
	@CrossOrigin(allowedHeaders = "*",origins = "*", 
   	methods=RequestMethod.GET)
	public ResponseEntity<ResponseWrapper> viewBookingsById(@PathVariable("bookingId") String bookingId){
		
		Bookings bookings= bookingsService.viewBookingsById(bookingId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseWrapper(bookings)); // Wraps the diagnosis object in a custom ResponseWrapper
	}
}
