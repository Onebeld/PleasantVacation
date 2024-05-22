package com.onebeld.pleasantvacation.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl {
    private final Path fileStorageLocation;

    public FileStorageServiceImpl() {
        this.fileStorageLocation = Paths.get("/images/tours").toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }

            String fileName = generateFileName(file);
            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation);

            return "/images/tours/" + fileName;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to store file.", ex);
        }
    }

    public Path load(String filename) {
        return fileStorageLocation.resolve(filename);
    }

    public Resource loadAsResource(String fileName) {
        try {
            Path file = load(fileName);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("Could not read file: " + fileName);
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + fileName, e);
        }
    }

    public void delete(String url) throws IOException {
        URI uri = URI.create(url);

        String fileName = Paths.get(uri.getPath()).getFileName().toString();
        Path path = fileStorageLocation.resolve(fileName);

        Files.delete(path);
    }

    private String generateFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = "";

        int i = originalFileName.lastIndexOf('.');
        if (i >= 0)
            extension = originalFileName.substring(i + 1);

        return UUID.randomUUID() + "." + extension;
    }
}
