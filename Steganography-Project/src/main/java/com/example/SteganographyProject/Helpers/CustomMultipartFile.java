package com.example.SteganographyProject.Helpers;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

public class CustomMultipartFile implements MultipartFile {
    private final byte[] input;
    private final String originalFilename;
    private final String contentType;

    public CustomMultipartFile(byte[] input, String originalFilename, String contentType) {
        this.input = input;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return Objects.requireNonNullElse(originalFilename, "unknown");
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return input == null || input.length == 0;
    }

    @Override
    public long getSize() {
        return input.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return input;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(input);
    }

    @Override
    public void transferTo(File destination) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            fos.write(input);
        }
    }
}
