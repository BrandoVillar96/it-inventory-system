package com.itinventory.backend.domain.repository;

import com.itinventory.backend.domain.entity.Equipment;
import com.itinventory.backend.domain.enums.EquipmentStatus;
import com.itinventory.backend.domain.enums.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
    List<Equipment> findByBranchId(UUID branchId);
    List<Equipment> findByAssignedToId(UUID personId);
    List<Equipment> findByStatus(EquipmentStatus status);
    List<Equipment> findByBranchIdAndStatus(UUID branchId, EquipmentStatus status);
    List<Equipment> findByType(EquipmentType type);
    boolean existsBySerialNumber(String serialNumber);
}