package com.itinventory.backend.application.mapper;

import com.itinventory.backend.application.dto.SupplyRequestDTO;
import com.itinventory.backend.application.dto.SupplyResponseDTO;
import com.itinventory.backend.domain.entity.Supply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplyMapper {

    @Mapping(target = "branch", ignore = true)
    Supply toEntity(SupplyRequestDTO dto);

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    @Mapping(expression = "java(supply.getQuantity() <= supply.getMinStock())", target = "lowStock")
    SupplyResponseDTO toDTO(Supply supply);

    @Mapping(target = "branch", ignore = true)
    void updateEntity(SupplyRequestDTO dto, @MappingTarget Supply supply);
}