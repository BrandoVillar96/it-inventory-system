package com.itinventory.backend.presentation.controller;

import com.itinventory.backend.application.dto.PersonRequestDTO;
import com.itinventory.backend.application.dto.PersonResponseDTO;
import com.itinventory.backend.application.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<PersonResponseDTO> create(@Valid @RequestBody PersonRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDTO>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<PersonResponseDTO>> findByBranch(@PathVariable UUID branchId) {
        return ResponseEntity.ok(personService.findByBranch(branchId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> update(@PathVariable UUID id,
                                                    @Valid @RequestBody PersonRequestDTO dto) {
        return ResponseEntity.ok(personService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}