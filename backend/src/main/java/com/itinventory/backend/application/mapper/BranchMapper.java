package com.itinventory.backend.application.mapper;

import com.itinventory.backend.application.dto.BranchRequestDTO;
import com.itinventory.backend.application.dto.BranchResponseDTO;
import com.itinventory.backend.domain.entity.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Branch toEntity(BranchRequestDTO dto);

    BranchResponseDTO toDTO(Branch branch);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(BranchRequestDTO dto, @MappingTarget Branch branch);
}