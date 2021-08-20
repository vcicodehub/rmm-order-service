package com.signet.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class RMMControllerTests {

	@Test
	void testPing() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getForObject("http://localhost:8080/api/v1/health", Object.class);
	}

}
