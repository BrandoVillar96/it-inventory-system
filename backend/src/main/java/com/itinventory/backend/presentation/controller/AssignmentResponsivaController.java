package com.itinventory.backend.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itinventory.backend.application.dto.AssignmentResponsivaRequestDTO;
import com.itinventory.backend.application.dto.AssignmentResponsivaResponseDTO;
import com.itinventory.backend.application.service.AssignmentResponsivaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.itinventory.backend.infrastructure.config.FileStorageService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/responsivas/assignments")
@RequiredArgsConstructor
public class AssignmentResponsivaController {

    private final AssignmentResponsivaService service;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentResponsivaResponseDTO> create(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "pdf", required = false) MultipartFile pdf,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {

        AssignmentResponsivaRequestDTO dto =
                objectMapper.readValue(dataJson, AssignmentResponsivaRequestDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto, pdf, photo));
    }

    @GetMapping
    public ResponseEntity<List<AssignmentResponsivaResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponsivaResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/file/**")
    public ResponseEntity<Resource> serveFile(@RequestParam String path) throws MalformedURLException {
        Path filePath = fileStorageService.load(path);
        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}