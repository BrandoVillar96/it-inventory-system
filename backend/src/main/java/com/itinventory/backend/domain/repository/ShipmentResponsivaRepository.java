package com.itinventory.backend.domain.repository;

import com.itinventory.backend.domain.entity.ShipmentResponsiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShipmentResponsivaRepository extends JpaRepository<ShipmentResponsiva, UUID> {
    List<ShipmentResponsiva> findAllByOrderByCreatedAtDesc();
    boolean existsByFolio(String folio);
}