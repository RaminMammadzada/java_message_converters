package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class FileController {
    private static final String PDF_FILE_PATH = "src/main/resources/sample.pdf";


    @GetMapping("/files/{enquiryId}/{customerId}")
    public ResponseEntity<File> downloadFile(@PathVariable Long enquiryId, @PathVariable Long customerId) throws IOException {
        // Retrieve the PDF file based on enquiryId and customerId
        File pdfFile = getPdfFile();

        // Check if the file exists
        if (pdfFile == null || !pdfFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Return the PDF file
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"filename.pdf\"")
                .body(pdfFile);
    }

    private File getPdfFile1(Long enquiryId, Long customerId) throws IOException {
        // Replace this with your logic to fetch the PDF file
        // Example: Reading the PDF file from the filesystem
        return getFileContent(enquiryId.toString(), customerId.toString());
    }

    private File getPdfFile() {
        // create sample pdf here directly instead of uploading from local memory

        return new File(PDF_FILE_PATH);
    }

    private File getFileContent(String enquiryId, String customerId) throws IOException {
        // Replace this with your logic to retrieve the actual file
        // based on enquiryId and customerId. This could involve
        // - Downloading the file from a remote server
        // - Reading the file from a local storage system
        // - Generating the file dynamically based on data

        // For demonstration purposes, we'll create a temporary file
        File tempFile = File.createTempFile("enquiry_" + enquiryId + "_customer_" + customerId, ".txt");
        try (Writer writer = new FileWriter(tempFile)) {
            writer.write("File content for enquiryId " + enquiryId + " and customerId " + customerId + " from Service B");
        }
        return tempFile;
    }
}
