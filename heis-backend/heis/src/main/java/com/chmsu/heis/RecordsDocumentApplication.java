package com.chmsu.heis;

import com.chmsu.heis.model.document.Document;
import com.chmsu.heis.services.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.sql.Date;
import java.util.Arrays;

@SpringBootApplication
@Slf4j
//	public class RecordsDocumentApplication implements CommandLineRunner {
public class RecordsDocumentApplication {

	@Autowired
	private DocumentService documentService;
//
	public static void main(String[] args) {
		SpringApplication.run(RecordsDocumentApplication.class, args);
	}
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}


//	@Override
//	public void run(String... args) throws Exception {
//		// Getting the Next Document Number
//		Long documentNumber = this.documentService.getNextDocumentNumber();
//		System.out.println("Next Document Number: " + documentNumber);
//
//		// Sending Email
//		Document document = new Document();
//		document.setDocumentId(1);
//		document.setDocumentNumber(1001);
//		document.setSubject("Sample Document");
//		document.setDateOfLetter(new Date(2024-6-27));
//		document.setType(1);
//		document.setAttention("To whom it may concern");
//		document.setThrough("Courier");
//		document.setFrom("Sender Name");
//		document.setPageCount(5);
//		document.setAttachment("link");
//		document.setCampus(1);
//		document.setDepartmentId(101);
//		document.setCc(Arrays.asList("Test CC", "Test CC 2", "Test CC 3")); // Set the cc list
//		document.setActionDate(new Date(2024- 6 -27));
//		document.setStatus(1);
//		document.setDateFinished(new Date(2024- 6 -27));
//		document.setEncoder(100);
//		document.setTimestamp(new Date(2024- 6 -27));
//
//		// Printing document details using Lombok-generated toString method
//		log.info("Document details: {}", document);
//
//		// Example of accessing individual fields using Lombok-generated getter methods
//		log.info("Document subject: {}", document.getSubject());
//		log.info("Document date of letter: {}", document.getDateOfLetter());
//		log.info("Document cc list: {}", document.getCc());
//		documentService.sendIndividualDocument(document);
//	}

}

