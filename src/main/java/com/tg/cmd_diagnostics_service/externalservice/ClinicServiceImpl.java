package com.tg.cmd_diagnostics_service.externalservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.tg.cmd_diagnostics_service.models.Bookings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClinicServiceImpl implements IClinicService{
	
	@Autowired
	private RestTemplate restTemplate;
	private ResponseEntity<String> response;
	
	@Value("${clinicApiUrl}")
    private String clinicApiUrl; // URL for inter-service API call to check if the clinic offers a service

	@Override
	public boolean isServiceOfferedByClinic(Bookings bookings) {
		
		// URL to check if the service is offered
        String serviceCheckUrl = clinicApiUrl + "/services/" + bookings.getBookingId();
        
        response = restTemplate.exchange(serviceCheckUrl, HttpMethod.GET, null, String.class);
        log.info("Response" + response.getBody());
        
     // Check the response and parse it to determine if the service is offered
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JsonParser parser = JsonParserFactory.getJsonParser();
            List<Object> rawList = parser.parseList(response.getBody());

            for (Object obj : rawList) {
                if (obj instanceof Map) {  
					Map<String, Object> service = (Map<String, Object>) obj;
                    Object serviceId = service.get("serviceId");
                    if (serviceId != null && serviceId.toString().equals(bookings.getBookingId().toString())) {
                        return true; // Service is offered
                    }
                }
            }
        }
		return false;
	}

}
