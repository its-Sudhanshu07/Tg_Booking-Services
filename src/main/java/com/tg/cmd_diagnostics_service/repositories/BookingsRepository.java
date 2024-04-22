package com.tg.cmd_diagnostics_service.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tg.cmd_diagnostics_service.models.Bookings;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, String>{
	// JPA repository for managing Bookings entities with a primary key of type String
}
