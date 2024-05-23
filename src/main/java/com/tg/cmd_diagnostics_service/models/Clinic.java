package com.tg.cmd_diagnostics_service.models;


import java.util.List;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clinic {
	
	private String clinicId;
	private String clinicName;
	
	
}
