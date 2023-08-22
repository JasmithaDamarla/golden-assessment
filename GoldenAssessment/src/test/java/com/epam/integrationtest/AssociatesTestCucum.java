package com.epam.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import com.epam.dto.BatchesDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.epam.dto.AssociatesDTO;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@RunWith(SpringRunner.class)
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssociatesTestCucum {

	@LocalServerPort
	private int port;
	private String baseUrl = "http://localhost:";
	private final RestTemplate restTemplate = new RestTemplate();
	private ResponseEntity<List> responseEntity;

	@Given("associates {string}")
	public void associates(String gender) {
		baseUrl = baseUrl.concat(port + "").concat("/rd/associates/"+gender);
	}

	@When("requested to find associates")
	public void requestedToFindBatch() {
		responseEntity = restTemplate.getForEntity(baseUrl,List.class);
	}

	@Then("the status code should be {int}")
	public void giveBatchInfo(int expectedStatus) {
		assertEquals(expectedStatus, responseEntity.getStatusCode().value());
	}

}
