package com.zettlmeier.speedrunner.entities.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class RestResponseEntityBuilder {
    private static final String API_BASE_URL = "/";

    public <T> ResponseEntity<T> buildGETResponse(T body) { return ResponseEntity.ok(body); }

    public <T> ResponseEntity<T> buildGETResponseWithHeaders(T body, HttpHeaders headers) {
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(body);
    }

    public <T> ResponseEntity<T> buildPOSTResponse(T body, String relativeLocation) {
        URI location = URI.create(API_BASE_URL + relativeLocation);
        return ResponseEntity.created(location).body(body);
    }

    public <T> ResponseEntity<T> buildAcceptedResponse(T body, String relativeLocation) {
        URI location = URI.create(API_BASE_URL + relativeLocation);
        return ResponseEntity.accepted().location(location).body(body);
    }

    public <T> ResponseEntity<T> buildPUTResponse(T body, String relativeLocation) {
        URI location = URI.create(API_BASE_URL + relativeLocation);
        return ResponseEntity.status(HttpStatus.OK).location(location).body(body);
    }

    public ResponseEntity<Void> buildDELETEResponse() { return ResponseEntity.noContent().build(); }
}
