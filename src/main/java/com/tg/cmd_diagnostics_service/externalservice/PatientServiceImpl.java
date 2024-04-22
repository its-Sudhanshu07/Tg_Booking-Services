package com.tg.cmd_diagnostics_service.externalservice;

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
public class PatientServiceImpl implements IPatientService{
	
	@Autowired
    private RestTemplate restTemplate;
	private ResponseEntity<String> response;
	
	@Value("${patientApiUrl}")
    private String patientApiUrl; // Base URL for patient service API
	
	@Override
	public boolean getPatientStatusFromPatientApi(Bookings bookings) {
		
		// REST call to get patient status based on patientId
        String url = String.format("%s/patient/%s/status", patientApiUrl, bookings.getPatientId());
        response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        log.info("Response" + response.getBody());
        
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JsonParser parser = JsonParserFactory.getJsonParser();
            Map<String, Object> data = parser.parseMap(response.getBody());

            // Boolean key "isActive" to indicate patient status
            if (data.containsKey("isActive")) {
                return Boolean.parseBoolean(data.get("isActive").toString());
            }
        }
		return false;
	}

}
