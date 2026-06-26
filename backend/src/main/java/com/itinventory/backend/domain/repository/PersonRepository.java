package com.itinventory.backend.domain.repository;

import com.itinventory.backend.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    List<Person> findByActiveTrueAndBranchId(UUID branchId);
    List<Person> findByActiveTrue();
    Optional<Person> findByEmail(String email);
    boolean existsByEmail(String email);
}