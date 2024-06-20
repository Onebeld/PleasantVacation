package com.onebeld.pleasantvacation.services;

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

/**
 * This service is responsible for storing, loading and deleting files in the application.
 * Files are stored in the '/images/tours' directory, starting at the root of the disk.
 */
@Service
public class FileStorageService {
    /**
     * The location where the uploaded files will be stored.
     */
    private final Path fileStorageLocation;

    /**
     * Creates a new {@link FileStorageService} and creates a directory in which to store files.
     * If the directory already exists, no exception is thrown.
     *
     * @throws RuntimeException If you cannot create a directory in which to store the downloaded files.
     */
    public FileStorageService() {
        this.fileStorageLocation = Paths.get("/images/tours").toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * Saves the specified file.
     *
     * @param file Storage file
     * @return URL of the saved file
     * @throws RuntimeException If the file is empty or if the file cannot be saved
     */
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

    /**
     * Loads a file with the specified name.
     *
     * @param filename Name of the file to be uploaded
     * @return Path to the downloaded file
     */
    public Path load(String filename) {
        return fileStorageLocation.resolve(filename);
    }

    /**
     * Loads the file as a resource.
     *
     * @param fileName Name of the file to be uploaded
     * @return Resource representing the file
     * @throws RuntimeException If the file cannot be read
     */
    public Resource loadAsResource(String fileName) {
        try {
            Path file = load(fileName);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + fileName, e);
        }
    }

    /**
     * Deletes the file with the specified URL.
     *
     * @param url URL of the file to be deleted
     * @throws IOException If the file cannot be deleted
     */
    public void delete(String url) throws IOException {
        URI uri = URI.create(url);

        String fileName = Paths.get(uri.getPath()).getFileName().toString();
        Path path = fileStorageLocation.resolve(fileName);

        Files.delete(path);
    }

    /**
     * Generates a random name for the file.
     *
     * @param file File for name generation
     * @return Random name for the file
     */
    private String generateFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = "";

        int i = originalFileName.lastIndexOf('.');
        if (i >= 0)
            extension = originalFileName.substring(i + 1);

        return UUID.randomUUID() + "." + extension;
    }
}
