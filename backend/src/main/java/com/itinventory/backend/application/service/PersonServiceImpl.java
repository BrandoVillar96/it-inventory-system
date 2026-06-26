package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.PersonRequestDTO;
import com.itinventory.backend.application.dto.PersonResponseDTO;
import com.itinventory.backend.application.mapper.PersonMapper;
import com.itinventory.backend.domain.entity.Branch;
import com.itinventory.backend.domain.entity.Person;
import com.itinventory.backend.domain.repository.BranchRepository;
import com.itinventory.backend.domain.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final BranchRepository branchRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonResponseDTO create(PersonRequestDTO dto) {
        if (personRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe una persona con ese email");
        }
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        Person person = personMapper.toEntity(dto);
        person.setBranch(branch);
        return personMapper.toDTO(personRepository.save(person));
    }

    @Override
    public PersonResponseDTO update(UUID id, PersonRequestDTO dto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        personMapper.updateEntity(dto, person);
        person.setBranch(branch);
        return personMapper.toDTO(personRepository.save(person));
    }

    @Override
    public PersonResponseDTO findById(UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        return personMapper.toDTO(person);
    }

    @Override
    public List<PersonResponseDTO> findAll() {
        return personRepository.findByActiveTrue()
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonResponseDTO> findByBranch(UUID branchId) {
        return personRepository.findByActiveTrueAndBranchId(branchId)
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        person.setActive(false);
        personRepository.save(person);
    }
}