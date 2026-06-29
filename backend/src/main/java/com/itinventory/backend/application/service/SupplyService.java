package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.SupplyRequestDTO;
import com.itinventory.backend.application.dto.SupplyResponseDTO;
import com.itinventory.backend.domain.enums.SupplyType;

import java.util.List;
import java.util.UUID;

public interface SupplyService {
    SupplyResponseDTO create(SupplyRequestDTO dto);
    SupplyResponseDTO update(UUID id, SupplyRequestDTO dto);
    SupplyResponseDTO findById(UUID id);
    List<SupplyResponseDTO> findAll();
    List<SupplyResponseDTO> findByBranch(UUID branchId);
    List<SupplyResponseDTO> findByType(SupplyType type);
    List<SupplyResponseDTO> findLowStock();
    List<SupplyResponseDTO> findLowStockByBranch(UUID branchId);
    SupplyResponseDTO updateQuantity(UUID id, Integer quantity);
    void delete(UUID id);
}