package com.itinventory.backend.application.dto;

import com.itinventory.backend.domain.enums.EquipmentStatus;
import com.itinventory.backend.domain.enums.EquipmentType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EquipmentResponseDTO {
    private UUID id;
    private EquipmentType type;
    private String brand;
    private String model;
    private String serialNumber;
    private EquipmentStatus status;
    private UUID branchId;
    private String branchName;
    private UUID assignedToId;
    private String assignedToName;
    private LocalDate deliveryDate;
    private LocalDate lastMaintenance;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
