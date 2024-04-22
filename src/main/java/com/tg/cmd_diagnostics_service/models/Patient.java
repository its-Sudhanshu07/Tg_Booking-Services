package com.tg.cmd_diagnostics_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
	
	private String Name;
	private long mobileNo;
	private boolean isActive;
}
