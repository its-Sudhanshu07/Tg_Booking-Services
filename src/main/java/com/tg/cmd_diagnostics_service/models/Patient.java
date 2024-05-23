package com.tg.cmd_diagnostics_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
	
	private String patientId;
	private String patientName;
	private String phoneNumber;
	private String patientEmailId; 
	private boolean isActive;
}
