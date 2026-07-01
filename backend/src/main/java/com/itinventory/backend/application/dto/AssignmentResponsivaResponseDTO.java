package com.itinventory.backend.application.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AssignmentResponsivaResponseDTO {
    private UUID id;
    private String folio;
    private UUID personId;
    private String personName;
    private UUID equipmentId;
    private String equipmentInfo;
    private UUID branchId;
    private String branchName;
    private String photoPath;
    private String pdfPath;
    private String observations;
    private LocalDateTime createdAt;
}