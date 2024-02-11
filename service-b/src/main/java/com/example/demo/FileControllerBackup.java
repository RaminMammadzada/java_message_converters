package com.example.demo;

import jakarta.annotation.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


@RestController
public class FileControllerBackup {

    private static final String PDF_FILE_PATH = "src/main/resources/sample.pdf";

    @GetMapping("/files/{enquiryId}/download/{customerId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long enquiryId, @PathVariable Long customerId) {
        // Retrieve the PDF file based on enquiryId and customerId
        File pdfFile = getPdfFile();

        // Check if the file exists
        if (pdfFile == null || !pdfFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Wrap the file as a FileResource and return it
        FileResource fileResource = new FileResource(pdfFile);
        return ResponseEntity.ok()
                .headers(fileResource.getHeaders())
                .body((Resource) fileResource);
    }

    private File getPdfFile1() {
        // create sample pdf here directly instead of uploading from local memory

        return new File(PDF_FILE_PATH);
    }

    private File getPdfFile() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(PDF_FILE_PATH));
            document.open();
            document.add(new Paragraph("This is a sample PDF file."));
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return new File(PDF_FILE_PATH);
    }
}
