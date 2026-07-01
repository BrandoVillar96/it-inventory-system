package com.itinventory.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assignment_responsivas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponsiva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false, length = 50)
    private String folio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(name = "photo_path", length = 255)
    private String photoPath;

    @Column(name = "pdf_path", length = 255)
    private String pdfPath;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}