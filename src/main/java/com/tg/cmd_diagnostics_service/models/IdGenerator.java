package com.tg.cmd_diagnostics_service.models;

import java.time.LocalDate;
import java.util.Random;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IdGenerator implements IdentifierGenerator{

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		
		return "BK_"+new Random().nextInt(100000)+"_"+LocalDate.now().getYear();
	}
	
	
}
