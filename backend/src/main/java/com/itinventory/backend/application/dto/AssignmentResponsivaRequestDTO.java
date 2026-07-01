package com.itinventory.backend.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AssignmentResponsivaRequestDTO {

    @NotNull(message = "La persona es obligatoria")
    private UUID personId;

    @NotNull(message = "El equipo es obligatorio")
    private UUID equipmentId;

    @NotNull(message = "La sucursal es obligatoria")
    private UUID branchId;

    private String observations;
}