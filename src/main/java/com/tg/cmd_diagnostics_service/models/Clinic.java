package com.tg.cmd_diagnostics_service.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clinic {
	
	private String Address;	
	private double price;
	private List<ServicesOffered> servicesOffered;
}
