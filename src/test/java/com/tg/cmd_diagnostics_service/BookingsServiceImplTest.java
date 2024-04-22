package com.tg.cmd_diagnostics_service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tg.cmd_diagnostics_service.exceptions.DiagnosisNotFoundException;
import com.tg.cmd_diagnostics_service.models.Bookings;
import com.tg.cmd_diagnostics_service.models.ServicesOffered;
import com.tg.cmd_diagnostics_service.repositories.BookingsRepository;
import com.tg.cmd_diagnostics_service.services.impl.BookingsServiceImpl;

import java.util.Arrays;

public class BookingsServiceImplTest {

    private BookingsServiceImpl bookingsService;
    private BookingsRepository bookingsRepository;

    @BeforeEach
    public void setUp() {
    	
    	bookingsRepository = new InMemorybookingsRepository();
    	bookingsService = new BookingsServiceImpl();
    	bookingsService.bookingsRepository = bookingsRepository; // Simulate Autowired dependency
    }

    @Test
    public void testBookService_Success() {
    	
        Bookings bookings = new Bookings();
        bookings.setServices(Arrays.asList(ServicesOffered.BloodTest)); 

        Bookings result = bookingsService.bookService(bookings);

        assertNotNull(result, "Expected the diagnostic to be booked successfully.");
        assertEquals(bookings, result, "Expected the returned diagnostic to be the same as the provided one.");
    }

    @Test
    public void testBookService_ServiceNotAvailable() {
    	
        Bookings bookings = new Bookings();
        bookings.setServices(Arrays.asList(ServicesOffered.Xray)); 

        Exception exception = assertThrows(DiagnosisNotFoundException.class, () -> {
        	bookingsService.bookService(bookings);
        });

        assertEquals("Not Found", exception.getMessage(), "Expected a 'Not Found' exception for unavailable service.");
    }

    @Test
    public void testViewBookingsById_Found() {
    	
        Bookings bookings = new Bookings();
        bookings.setBookingId("12345");
        bookingsRepository.save(bookings); // Add to the repository for test

        Bookings result = bookingsService.viewBookingsById("12345");

        assertNotNull(result, "Expected to find the Bookings by its ID.");
        assertEquals(bookings, result, "Expected to retrieve the same Bookings that was saved.");
    }

    @Test
    public void testViewBookingsById_NotFound() {
    	
        Bookings result = bookingsService.viewBookingsById("non-existent");

        assertNull(result, "Expected to return null when Bookings ID is not found.");
    }

    @Test
    public void testCancelServiceFail() {
    	
        Bookings booking = new Bookings();
        booking.setBookingId("12345");
        bookingsRepository.save(booking); // Add to the repository for test

        boolean result = bookingsService.cancelService("123456");

        assertFalse(result, "Expected cancelService to return false indicating it doesn't delete the record.");
        assertNotNull(bookingsRepository.findById("12345").orElse(null), "Expected the diagnostic record to still exist.");
    }

}
