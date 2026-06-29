package com.itinventory.backend.presentation.controller;

import com.itinventory.backend.application.dto.SupplyRequestDTO;
import com.itinventory.backend.application.dto.SupplyResponseDTO;
import com.itinventory.backend.application.service.SupplyService;
import com.itinventory.backend.domain.enums.SupplyType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/supplies")
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyService supplyService;

    @PostMapping
    public ResponseEntity<SupplyResponseDTO> create(@Valid @RequestBody SupplyRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplyService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<SupplyResponseDTO>> findAll() {
        return ResponseEntity.ok(supplyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplyResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(supplyService.findById(id));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<SupplyResponseDTO>> findByBranch(@PathVariable UUID branchId) {
        return ResponseEntity.ok(supplyService.findByBranch(branchId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<SupplyResponseDTO>> findByType(@PathVariable SupplyType type) {
        return ResponseEntity.ok(supplyService.findByType(type));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<SupplyResponseDTO>> findLowStock() {
        return ResponseEntity.ok(supplyService.findLowStock());
    }

    @GetMapping("/low-stock/branch/{branchId}")
    public ResponseEntity<List<SupplyResponseDTO>> findLowStockByBranch(@PathVariable UUID branchId) {
        return ResponseEntity.ok(supplyService.findLowStockByBranch(branchId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplyResponseDTO> update(@PathVariable UUID id,
                                                    @Valid @RequestBody SupplyRequestDTO dto) {
        return ResponseEntity.ok(supplyService.update(id, dto));
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<SupplyResponseDTO> updateQuantity(@PathVariable UUID id,
                                                            @RequestParam Integer quantity) {
        return ResponseEntity.ok(supplyService.updateQuantity(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        supplyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}