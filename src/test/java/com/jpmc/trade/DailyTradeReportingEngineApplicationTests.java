package com.jpmc.trade;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.common.io.Resources;
import com.google.common.base.Charsets;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DailyTradeReportingEngineApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DailyTradeReportingEngineApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testDailyTradeReportingEngine() throws Exception {

		URL url = Resources.getResource("request.json");
		URL url1 = Resources.getResource("response.json");

		String request = Resources.toString(url, Charsets.UTF_8);
		String expectedResponse = Resources.toString(url1, Charsets.UTF_8);

		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
		String response = RestAssured.given().accept("application/json").contentType("application/json").body(request)
				.when().post("/generateTradeReport").then().statusCode(200).extract().response().asString();
		assertEquals(response, expectedResponse);
		System.out.println(response);

	}

}
