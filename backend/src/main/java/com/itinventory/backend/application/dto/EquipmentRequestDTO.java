package com.itinventory.backend.application.dto;

import com.itinventory.backend.domain.enums.EquipmentStatus;
import com.itinventory.backend.domain.enums.EquipmentType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class EquipmentRequestDTO {

    @NotNull(message = "El tipo de equipo es obligatorio")
    private EquipmentType type;

    private String brand;
    private String model;
    private String serialNumber;
    private EquipmentStatus status;

    @NotNull(message = "La sucursal es obligatoria")
    private UUID branchId;

    private UUID assignedToId;
    private LocalDate deliveryDate;
    private LocalDate lastMaintenance;
    private String notes;
}