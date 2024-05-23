package com.tg.cmd_diagnostics_service.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Bookings")
public class Bookings {
	
	@Id
	@GenericGenerator(name="booking_Id",strategy="com.tg.cmd_diagnostics_service.models.IdGenerator")
	@GeneratedValue(generator="booking_Id")
	@Column(name="booking_Id")
	private String bookingId;	
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name="booking_Date")
	private LocalDate bookingDate;
	
	@DateTimeFormat(iso= ISO.TIME)
	@Column(name="booking_Time")
	private LocalDateTime bookingTime;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "created_date")
	private LocalDate createdDate;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "modified_date")
	private LocalDate lastmodifiedDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String lastmodifiedBy;
	
	@Enumerated(EnumType.STRING)	
	@Column(name = "status")
	private Status status;
	
	@Autowired
	private Patient patient; 
	
	@Autowired
	private Clinic clinic;
	
	@Autowired
	@Column(name="Services_Offered")
	private List<Services> services;
}
