package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class FileService {

    private final RestTemplate restTemplate;

    @Autowired
    public FileService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public byte[] getFile(String enquiryId, String customerId) {
        String serviceBUrl = "http://localhost:5002/files/{enquiryId}/{customerId}";

        return restTemplate.getForObject(serviceBUrl, byte[].class, enquiryId, customerId);
    }
}