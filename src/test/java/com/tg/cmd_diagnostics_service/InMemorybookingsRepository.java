package com.tg.cmd_diagnostics_service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.tg.cmd_diagnostics_service.models.Bookings;
import com.tg.cmd_diagnostics_service.repositories.BookingsRepository;



public class InMemorybookingsRepository implements BookingsRepository {
    private List<Bookings> bookingsList= new ArrayList<>();

    @Override
    public Bookings save(Bookings bookings) {
    	bookingsList.add(bookings);
        return bookings;
    }

    @Override
    public Optional<Bookings> findById(String id) {
        return bookingsList.stream().filter(d -> d.getBookingId().equals(id)).findFirst();
    }
    
    @Override
    public void deleteById(String bookingId) { // Implemented delete by ID
        bookingsList.removeIf(b -> b.getBookingId().equals(bookingId));
    }
    
    
}
