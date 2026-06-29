package com.itinventory.backend.application.dto;

import com.itinventory.backend.domain.enums.SupplyType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SupplyRequestDTO {

    @NotNull(message = "El tipo de consumible es obligatorio")
    private SupplyType type;

    private String brand;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer quantity = 0;

    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer minStock = 5;

    @NotNull(message = "La sucursal es obligatoria")
    private UUID branchId;
}