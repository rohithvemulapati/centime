package com.centime.service1.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import com.centime.service1.dto.NameDTO;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

class ServiceOneControllerTest {

	private MockWebServer mockWebServer;
    private ServiceOneController serviceOneController;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start(9091);

        String baseUrl = mockWebServer.url("/").toString();

        WebClient webClient = WebClient.builder().build();
        serviceOneController = new ServiceOneController();
        
        // Use reflection to set private fields
        serviceOneController.getClass().getDeclaredField("webClient").setAccessible(true);
        serviceOneController.getClass().getDeclaredField("webClient").set(serviceOneController, webClient);
        
        serviceOneController.getClass().getDeclaredField("service2Url").setAccessible(true);
        serviceOneController.getClass().getDeclaredField("service2Url").set(serviceOneController, baseUrl + "service2");
        
        serviceOneController.getClass().getDeclaredField("service3Url").setAccessible(true);
        serviceOneController.getClass().getDeclaredField("service3Url").set(serviceOneController, baseUrl + "service3");
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void testGreetUserSuccess() {
        // Mock responses for Service 2 and Service 3
        mockWebServer.enqueue(new MockResponse()
                .setBody("Hello")
                .addHeader("Content-Type", "application/json"));

        mockWebServer.enqueue(new MockResponse()
                .setBody("John Doe")
                .addHeader("Content-Type", "application/json"));

        NameDTO nameDTO = new NameDTO();
        nameDTO.setName("John");
        nameDTO.setSurName("Doe");

        ResponseEntity<String> response = serviceOneController.greetUser(nameDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Hello, John Doe!", response.getBody());
    }

//    @BeforeEach
//    void setUp() {
//        serviceOneController = new ServiceOneController();
//        serviceOneController.webClient = webClient;
//        serviceOneController.service2Url = "http://localhost:8081/service2/api/v1";
//        serviceOneController.service3Url = "http://localhost:8082/service3/api/v1";
//    }

    @Test
    void testServiceOneStatus() {
        ResponseEntity<String> response = serviceOneController.serviceOne();
        assertEquals("Up", response.getBody());
    }
    
    @Test
    void testGreetUser_Service2Fails() {
        // Service 2 returns 500
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error"));

        // Service 3 won't be called because Service 2 fails
        NameDTO nameDTO = new NameDTO();
        nameDTO.setName("John");
        nameDTO.setSurName("Doe");

        ResponseEntity<String> response = serviceOneController.greetUser(nameDTO);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error while processing the request", response.getBody());
    }

}
