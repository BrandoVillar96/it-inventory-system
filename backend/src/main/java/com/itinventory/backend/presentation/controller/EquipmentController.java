package com.itinventory.backend.presentation.controller;

import com.itinventory.backend.application.dto.EquipmentRequestDTO;
import com.itinventory.backend.application.dto.EquipmentResponseDTO;
import com.itinventory.backend.application.service.EquipmentService;
import com.itinventory.backend.domain.enums.EquipmentStatus;
import com.itinventory.backend.domain.enums.EquipmentType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<EquipmentResponseDTO> create(@Valid @RequestBody EquipmentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<EquipmentResponseDTO>> findAll() {
        return ResponseEntity.ok(equipmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(equipmentService.findById(id));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<EquipmentResponseDTO>> findByBranch(@PathVariable UUID branchId) {
        return ResponseEntity.ok(equipmentService.findByBranch(branchId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EquipmentResponseDTO>> findByStatus(@PathVariable EquipmentStatus status) {
        return ResponseEntity.ok(equipmentService.findByStatus(status));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<EquipmentResponseDTO>> findByType(@PathVariable EquipmentType type) {
        return ResponseEntity.ok(equipmentService.findByType(type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> update(@PathVariable UUID id,
                                                       @Valid @RequestBody EquipmentRequestDTO dto) {
        return ResponseEntity.ok(equipmentService.update(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EquipmentResponseDTO> updateStatus(@PathVariable UUID id,
                                                             @RequestParam EquipmentStatus status) {
        return ResponseEntity.ok(equipmentService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        equipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}