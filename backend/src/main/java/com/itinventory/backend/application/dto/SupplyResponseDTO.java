package com.itinventory.backend.application.dto;

import com.itinventory.backend.domain.enums.SupplyType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SupplyResponseDTO {
    private UUID id;
    private SupplyType type;
    private String brand;
    private Integer quantity;
    private Integer minStock;
    private boolean lowStock;
    private UUID branchId;
    private String branchName;
    private LocalDateTime createdAt;
}