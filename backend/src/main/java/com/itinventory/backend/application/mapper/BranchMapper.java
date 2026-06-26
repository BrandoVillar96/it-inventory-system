package com.itinventory.backend.application.mapper;

import com.itinventory.backend.application.dto.BranchRequestDTO;
import com.itinventory.backend.application.dto.BranchResponseDTO;
import com.itinventory.backend.domain.entity.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    Branch toEntity(BranchRequestDTO dto);
    BranchResponseDTO toDTO(Branch branch);
    void updateEntity(BranchRequestDTO dto, @MappingTarget Branch branch);
}