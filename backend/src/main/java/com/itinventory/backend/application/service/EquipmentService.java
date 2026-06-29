package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.EquipmentRequestDTO;
import com.itinventory.backend.application.dto.EquipmentResponseDTO;
import com.itinventory.backend.domain.enums.EquipmentStatus;
import com.itinventory.backend.domain.enums.EquipmentType;

import java.util.List;
import java.util.UUID;

public interface EquipmentService {
    EquipmentResponseDTO create(EquipmentRequestDTO dto);
    EquipmentResponseDTO update(UUID id, EquipmentRequestDTO dto);
    EquipmentResponseDTO findById(UUID id);
    List<EquipmentResponseDTO> findAll();
    List<EquipmentResponseDTO> findByBranch(UUID branchId);
    List<EquipmentResponseDTO> findByStatus(EquipmentStatus status);
    List<EquipmentResponseDTO> findByType(EquipmentType type);
    EquipmentResponseDTO updateStatus(UUID id, EquipmentStatus status);
    void delete(UUID id);
}