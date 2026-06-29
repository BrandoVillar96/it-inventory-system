package com.itinventory.backend.domain.entity;

import com.itinventory.backend.domain.enums.EquipmentStatus;
import com.itinventory.backend.domain.enums.EquipmentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "equipment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EquipmentType type;

    @Column(length = 100)
    private String brand;

    @Column(length = 100)
    private String model;

    @Column(name = "serial_number", unique = true, length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(length = 50)
    private EquipmentStatus status = EquipmentStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private Person assignedTo;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "last_maintenance")
    private LocalDate lastMaintenance;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}