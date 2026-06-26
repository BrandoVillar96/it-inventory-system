package com.itinventory.backend.application.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PersonResponseDTO {
    private UUID id;
    private String fullName;
    private String position;
    private String email;
    private UUID branchId;
    private String branchName;
    private Boolean active;
    private LocalDateTime createdAt;
}