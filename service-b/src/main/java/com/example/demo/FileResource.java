package com.example.demo;

import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileResource extends AbstractResource {

    private final File file;

    public FileResource(File file) {
        this.file = file;
    }

    @Override
    public String getFilename() {
        return file.getName();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public byte[] getContentAsByteArray() throws IOException {
        return super.getContentAsByteArray();
    }

    @Override
    public String getContentAsString(Charset charset) throws IOException {
        return super.getContentAsString(charset);
    }

    @Override
    public long contentLength() throws IOException {
        return Files.size(file.toPath());
    }

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(file.length());
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        return headers;
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        throw new UnsupportedOperationException();
    }

    public HttpHeaders getResponseHeaders() {
        return this.getHeaders();
    }
}
