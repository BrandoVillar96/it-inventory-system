package com.itinventory.backend.application.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ShipmentResponsivaResponseDTO {
    private UUID id;
    private String folio;
    private UUID originBranchId;
    private String originBranchName;
    private UUID destinationBranchId;
    private String destinationBranchName;
    private LocalDate shipDate;
    private String senderName;
    private String receiverName;
    private String barcodeFolio;
    private String pdfPath;
    private String notes;
    private LocalDateTime createdAt;
}