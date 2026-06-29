package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.EquipmentRequestDTO;
import com.itinventory.backend.application.dto.EquipmentResponseDTO;
import com.itinventory.backend.application.mapper.EquipmentMapper;
import com.itinventory.backend.domain.entity.Branch;
import com.itinventory.backend.domain.entity.Equipment;
import com.itinventory.backend.domain.entity.Person;
import com.itinventory.backend.domain.enums.EquipmentStatus;
import com.itinventory.backend.domain.enums.EquipmentType;
import com.itinventory.backend.domain.repository.BranchRepository;
import com.itinventory.backend.domain.repository.EquipmentRepository;
import com.itinventory.backend.domain.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final BranchRepository branchRepository;
    private final PersonRepository personRepository;
    private final EquipmentMapper equipmentMapper;

    @Override
    public EquipmentResponseDTO create(EquipmentRequestDTO dto) {
        if (dto.getSerialNumber() != null && equipmentRepository.existsBySerialNumber(dto.getSerialNumber())) {
            throw new RuntimeException("Ya existe un equipo con ese número de serie");
        }
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        Equipment equipment = equipmentMapper.toEntity(dto);
        if (equipment.getStatus() == null) {
            equipment.setStatus(EquipmentStatus.ACTIVE);
        }
        equipment.setBranch(branch);
        if (dto.getAssignedToId() != null) {
            Person person = personRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
            equipment.setAssignedTo(person);
        }
        return equipmentMapper.toDTO(equipmentRepository.save(equipment));
    }

    @Override
    public EquipmentResponseDTO update(UUID id, EquipmentRequestDTO dto) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        equipmentMapper.updateEntity(dto, equipment);
        equipment.setBranch(branch);
        if (dto.getAssignedToId() != null) {
            Person person = personRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
            equipment.setAssignedTo(person);
        } else {
            equipment.setAssignedTo(null);
        }
        return equipmentMapper.toDTO(equipmentRepository.save(equipment));
    }

    @Override
    public EquipmentResponseDTO findById(UUID id) {
        return equipmentMapper.toDTO(equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado")));
    }

    @Override
    public List<EquipmentResponseDTO> findAll() {
        return equipmentRepository.findAll()
                .stream()
                .map(equipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EquipmentResponseDTO> findByBranch(UUID branchId) {
        return equipmentRepository.findByBranchId(branchId)
                .stream()
                .map(equipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EquipmentResponseDTO> findByStatus(EquipmentStatus status) {
        return equipmentRepository.findByStatus(status)
                .stream()
                .map(equipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EquipmentResponseDTO> findByType(EquipmentType type) {
        return equipmentRepository.findByType(type)
                .stream()
                .map(equipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentResponseDTO updateStatus(UUID id, EquipmentStatus status) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        equipment.setStatus(status);
        return equipmentMapper.toDTO(equipmentRepository.save(equipment));
    }

    @Override
    public void delete(UUID id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        equipment.setStatus(EquipmentStatus.RETIRED);
        equipmentRepository.save(equipment);
    }
}