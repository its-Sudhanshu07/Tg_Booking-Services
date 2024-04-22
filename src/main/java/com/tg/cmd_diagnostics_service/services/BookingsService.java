package com.tg.cmd_diagnostics_service.services;

import com.tg.cmd_diagnostics_service.models.Bookings;

public interface BookingsService {
	
	// Method to book a new service, returning the created Bookings object
	Bookings bookService(Bookings bookings);
	
	/*
     * Cancels a booking based on its ID.
     *
     * @param bookingId The ID of the booking to be canceled.
     * @return True if the booking was successfully canceled, false otherwise.
     */
	boolean cancelService(String bookingId);
	
	// Method to retrieve a booking by its ID
	Bookings viewBookingsById(String bookingId);
	
}
