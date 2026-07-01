package com.itinventory.backend.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        try {
            rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de archivos", e);
        }
    }

    public String store(MultipartFile file, String subfolder) {
        try {
            if (file == null || file.isEmpty()) return null;

            Path folder = rootLocation.resolve(subfolder);
            Files.createDirectories(folder);

            String original = file.getOriginalFilename();
            String extension = "";
            if (original != null && original.contains(".")) {
                extension = original.substring(original.lastIndexOf("."));
            }
            String filename = UUID.randomUUID() + extension;
            Path destination = folder.resolve(filename);

            file.transferTo(destination);
            return subfolder + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo", e);
        }
    }

    public Path load(String relativePath) {
        return rootLocation.resolve(relativePath).normalize();
    }
}