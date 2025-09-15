package com.smartfactory.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication  // com.smartfactory.erp.** 전체 스캔
public class ErpServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ErpServiceApplication.class, args);

	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}


