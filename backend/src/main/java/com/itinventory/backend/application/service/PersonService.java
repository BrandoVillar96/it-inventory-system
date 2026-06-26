package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.PersonRequestDTO;
import com.itinventory.backend.application.dto.PersonResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    PersonResponseDTO create(PersonRequestDTO dto);
    PersonResponseDTO update(UUID id, PersonRequestDTO dto);
    PersonResponseDTO findById(UUID id);
    List<PersonResponseDTO> findAll();
    List<PersonResponseDTO> findByBranch(UUID branchId);
    void delete(UUID id);
}