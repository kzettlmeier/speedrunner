package com.zettlmeier.speedrunner.entities.service;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class RestResponseEntityBuilderTest {
    private RestResponseEntityBuilder restResponseEntityBuilder = new RestResponseEntityBuilder();

    @Test
    public void buildGETTest() {
        ResponseEntity response = restResponseEntityBuilder.buildGETResponse("response");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("response");
    }

    @Test
    public void buildPOSTTest() {
        ResponseEntity response = restResponseEntityBuilder.buildPOSTResponse("response", "path");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().get("Location").get(0)).isEqualTo("/path");
        assertThat(response.getBody()).isEqualTo("response");
    }

    @Test
    public void buildPUTTest() {
        ResponseEntity response = restResponseEntityBuilder.buildPUTResponse("response", "path");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get("Location").get(0)).isEqualTo("/path");
        assertThat(response.getBody()).isEqualTo("response");
    }
}
