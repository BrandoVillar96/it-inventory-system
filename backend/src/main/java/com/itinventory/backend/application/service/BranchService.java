package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.BranchRequestDTO;
import com.itinventory.backend.application.dto.BranchResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BranchService {
    BranchResponseDTO create(BranchRequestDTO dto);
    BranchResponseDTO update(UUID id, BranchRequestDTO dto);
    BranchResponseDTO findById(UUID id);
    List<BranchResponseDTO> findAll();
    void delete(UUID id);
}