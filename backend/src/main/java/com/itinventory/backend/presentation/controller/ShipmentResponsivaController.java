package com.itinventory.backend.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itinventory.backend.application.dto.ShipmentResponsivaRequestDTO;
import com.itinventory.backend.application.dto.ShipmentResponsivaResponseDTO;
import com.itinventory.backend.application.service.ShipmentResponsivaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/responsivas/shipments")
@RequiredArgsConstructor
public class ShipmentResponsivaController {

    private final ShipmentResponsivaService service;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ShipmentResponsivaResponseDTO> create(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "pdf", required = false) MultipartFile pdf) throws IOException {

        ShipmentResponsivaRequestDTO dto =
                objectMapper.readValue(dataJson, ShipmentResponsivaRequestDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto, pdf));
    }

    @GetMapping
    public ResponseEntity<List<ShipmentResponsivaResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponsivaResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }
}