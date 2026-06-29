package com.itinventory.backend.domain.entity;

import com.itinventory.backend.domain.enums.SupplyType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "supplies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SupplyType type;

    @Column(length = 100)
    private String brand;

    @Builder.Default
    private Integer quantity = 0;

    @Column(name = "min_stock")
    @Builder.Default
    private Integer minStock = 5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}