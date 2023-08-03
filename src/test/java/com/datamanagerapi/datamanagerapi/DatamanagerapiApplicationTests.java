package com.datamanagerapi.datamanagerapi;

import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class DatamanagerapiApplicationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    void contextLoads() {
    }

   @Test
    public void userLoginTest()
    {
      /*  String plainCredentials = "api_user:t5p4krGSJyil2hWB";
        byte[] plainCredentialsBytes = plainCredentials.getBytes();
        byte[] base64CredentialsBytes = Base64.getEncoder().encode(plainCredentialsBytes);
        String base64Credentials = new String(base64CredentialsBytes);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + base64Credentials);
        httpHeaders.add("Content-Type", "application/json");
        String url ="http://localhost:8006/api/v1/accountsManagement/login";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("87665");
        loginRequest.setUserName("kitty");
        ResponseEntity<Object> response = testRestTemplate.postForEntity(url, loginRequest, Object.class);
        System.out.println("Show test status :"+response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);*/

    }
}
