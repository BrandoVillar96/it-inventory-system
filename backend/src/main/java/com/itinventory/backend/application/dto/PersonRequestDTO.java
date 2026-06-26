package com.itinventory.backend.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class PersonRequestDTO {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String fullName;

    @Size(max = 100, message = "El puesto no puede superar 100 caracteres")
    private String position;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene formato válido")
    private String email;

    @NotNull(message = "La sucursal es obligatoria")
    private UUID branchId;
}