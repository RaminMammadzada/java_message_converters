package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import org.springframework.core.io.FileSystemResource;
@RestController
public class FileController {

    private final RestTemplate restTemplate;

    public FileController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/enquiries/{enquiryId}/download/{customerId}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable Long enquiryId, @PathVariable Long customerId) {
        // Call Service B to download the file
        String serviceBUrl = "http://localhost:5002/files/" + enquiryId + "/" + customerId;
        ResponseEntity<File> responseEntity = restTemplate.getForEntity(serviceBUrl, File.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            File file = responseEntity.getBody();
            FileSystemResource resource = new FileSystemResource(file);

            // Set appropriate headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", file.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode()).build();
        }
    }
}
