package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.SupplyRequestDTO;
import com.itinventory.backend.application.dto.SupplyResponseDTO;
import com.itinventory.backend.application.mapper.SupplyMapper;
import com.itinventory.backend.domain.entity.Branch;
import com.itinventory.backend.domain.entity.Supply;
import com.itinventory.backend.domain.enums.SupplyType;
import com.itinventory.backend.domain.repository.BranchRepository;
import com.itinventory.backend.domain.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplyServiceImpl implements SupplyService {

    private final SupplyRepository supplyRepository;
    private final BranchRepository branchRepository;
    private final SupplyMapper supplyMapper;

    @Override
    public SupplyResponseDTO create(SupplyRequestDTO dto) {
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        Supply supply = supplyMapper.toEntity(dto);
        supply.setBranch(branch);
        return supplyMapper.toDTO(supplyRepository.save(supply));
    }

    @Override
    public SupplyResponseDTO update(UUID id, SupplyRequestDTO dto) {
        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumible no encontrado"));
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        supplyMapper.updateEntity(dto, supply);
        supply.setBranch(branch);
        return supplyMapper.toDTO(supplyRepository.save(supply));
    }

    @Override
    public SupplyResponseDTO findById(UUID id) {
        return supplyMapper.toDTO(supplyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumible no encontrado")));
    }

    @Override
    public List<SupplyResponseDTO> findAll() {
        return supplyRepository.findAll()
                .stream()
                .map(supplyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplyResponseDTO> findByBranch(UUID branchId) {
        return supplyRepository.findByBranchId(branchId)
                .stream()
                .map(supplyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplyResponseDTO> findByType(SupplyType type) {
        return supplyRepository.findByType(type)
                .stream()
                .map(supplyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplyResponseDTO> findLowStock() {
        return supplyRepository.findLowStock()
                .stream()
                .map(supplyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplyResponseDTO> findLowStockByBranch(UUID branchId) {
        return supplyRepository.findLowStockByBranch(branchId)
                .stream()
                .map(supplyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplyResponseDTO updateQuantity(UUID id, Integer quantity) {
        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumible no encontrado"));
        supply.setQuantity(quantity);
        return supplyMapper.toDTO(supplyRepository.save(supply));
    }

    @Override
    public void delete(UUID id) {
        supplyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumible no encontrado"));
        supplyRepository.deleteById(id);
    }
}