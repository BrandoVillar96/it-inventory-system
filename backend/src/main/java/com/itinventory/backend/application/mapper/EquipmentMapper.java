package com.itinventory.backend.application.mapper;

import com.itinventory.backend.application.dto.EquipmentRequestDTO;
import com.itinventory.backend.application.dto.EquipmentResponseDTO;
import com.itinventory.backend.domain.entity.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Equipment toEntity(EquipmentRequestDTO dto);

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "assignedTo.fullName", target = "assignedToName")
    EquipmentResponseDTO toDTO(Equipment equipment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(EquipmentRequestDTO dto, @MappingTarget Equipment equipment);
}