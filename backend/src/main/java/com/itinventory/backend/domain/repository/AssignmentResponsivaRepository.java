package com.itinventory.backend.domain.repository;

import com.itinventory.backend.domain.entity.AssignmentResponsiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentResponsivaRepository extends JpaRepository<AssignmentResponsiva, UUID> {
    List<AssignmentResponsiva> findAllByOrderByCreatedAtDesc();
    boolean existsByFolio(String folio);
}