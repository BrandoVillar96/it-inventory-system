package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.BranchRequestDTO;
import com.itinventory.backend.application.dto.BranchResponseDTO;
import com.itinventory.backend.application.mapper.BranchMapper;
import com.itinventory.backend.domain.entity.Branch;
import com.itinventory.backend.domain.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public BranchResponseDTO create(BranchRequestDTO dto) {
        if (branchRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new RuntimeException("Ya existe una sucursal con ese nombre");
        }
        Branch branch = branchMapper.toEntity(dto);
        return branchMapper.toDTO(branchRepository.save(branch));
    }

    @Override
    public BranchResponseDTO update(UUID id, BranchRequestDTO dto) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        branchMapper.updateEntity(dto, branch);
        return branchMapper.toDTO(branchRepository.save(branch));
    }

    @Override
    public BranchResponseDTO findById(UUID id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        return branchMapper.toDTO(branch);
    }

    @Override
    public List<BranchResponseDTO> findAll() {
        return branchRepository.findByActiveTrue()
                .stream()
                .map(branchMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        branch.setActive(false);
        branchRepository.save(branch);
    }
}
