package com.itinventory.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shipment_responsivas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponsiva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false, length = 50)
    private String folio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_branch_id")
    private Branch originBranch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_branch_id")
    private Branch destinationBranch;

    @Column(name = "ship_date")
    private LocalDate shipDate;

    @Column(name = "sender_name", length = 150)
    private String senderName;

    @Column(name = "receiver_name", length = 150)
    private String receiverName;

    @Column(name = "barcode_folio", length = 50)
    private String barcodeFolio;

    @Column(name = "pdf_path", length = 255)
    private String pdfPath;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}