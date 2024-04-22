package com.tg.cmd_diagnostics_service.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//Generic class that can hold any type of object
public class ResponseWrapper<T> {
	
	// A message field for additional information
	private String message;
	// A generic field to hold any type of object
	private T object; 
	
	public ResponseWrapper(T object) {
		
		super();
		this.object = object; // Sets the object passed as a parameter
	}
	
	public ResponseWrapper(String message) {
		
		super();
		this.message = message; // Sets the message passed as a parameter
	}

}

