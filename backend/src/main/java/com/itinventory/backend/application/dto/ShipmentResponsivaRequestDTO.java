package com.itinventory.backend.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ShipmentResponsivaRequestDTO {

    @NotNull(message = "La sucursal origen es obligatoria")
    private UUID originBranchId;

    @NotNull(message = "La sucursal destino es obligatoria")
    private UUID destinationBranchId;

    private LocalDate shipDate;
    private String senderName;
    private String receiverName;
    private String barcodeFolio;
    private String notes;
}