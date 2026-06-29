package com.itinventory.backend.domain.repository;

import com.itinventory.backend.domain.entity.Supply;
import com.itinventory.backend.domain.enums.SupplyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, UUID> {
    List<Supply> findByBranchId(UUID branchId);
    List<Supply> findByType(SupplyType type);

    @Query("SELECT s FROM Supply s WHERE s.quantity <= s.minStock")
    List<Supply> findLowStock();

    @Query("SELECT s FROM Supply s WHERE s.branch.id = :branchId AND s.quantity <= s.minStock")
    List<Supply> findLowStockByBranch(UUID branchId);
}