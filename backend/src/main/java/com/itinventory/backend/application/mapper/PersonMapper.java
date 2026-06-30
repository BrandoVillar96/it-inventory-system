package com.itinventory.backend.application.mapper;

import com.itinventory.backend.application.dto.PersonRequestDTO;
import com.itinventory.backend.application.dto.PersonResponseDTO;
import com.itinventory.backend.domain.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Person toEntity(PersonRequestDTO dto);

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    PersonResponseDTO toDTO(Person person);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(PersonRequestDTO dto, @MappingTarget Person person);
}