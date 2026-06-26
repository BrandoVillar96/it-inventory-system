package com.itinventory.backend.presentation.controller;

import com.itinventory.backend.application.dto.BranchRequestDTO;
import com.itinventory.backend.application.dto.BranchResponseDTO;
import com.itinventory.backend.application.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchResponseDTO> create(@Valid @RequestBody BranchRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(branchService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<BranchResponseDTO>> findAll() {
        return ResponseEntity.ok(branchService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(branchService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> update(@PathVariable UUID id,
                                                    @Valid @RequestBody BranchRequestDTO dto) {
        return ResponseEntity.ok(branchService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        branchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}