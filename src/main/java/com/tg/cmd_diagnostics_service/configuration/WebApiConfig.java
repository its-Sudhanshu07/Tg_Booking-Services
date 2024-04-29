package com.tg.cmd_diagnostics_service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebApiConfig implements WebMvcConfigurer{


    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
    	 
         // Configures content negotiation for HTTP requests
    	 configurer
         .favorPathExtension(true) // Allows content negotiation via file extension (e.g., /data.json)
         .favorParameter(true) // Allows content negotiation via a query parameter (e.g., ?mediaType=json)
         .parameterName("mediaType") // Sets the name of the query parameter for content negotiation
         .ignoreAcceptHeader(false) // Considers the Accept header for content negotiation
         .useRegisteredExtensionsOnly(false) // Allows non-registered extensions for content negotiation
         .defaultContentType(MediaType.APPLICATION_JSON) // Sets the default content type to JSON
         .mediaType("xml", MediaType.APPLICATION_XML) // Maps "xml" to the XML media type
         .mediaType("json", MediaType.APPLICATION_JSON); // Maps "json" to the JSON media type
 }


}