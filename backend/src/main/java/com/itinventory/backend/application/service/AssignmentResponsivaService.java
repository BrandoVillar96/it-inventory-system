package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.AssignmentResponsivaRequestDTO;
import com.itinventory.backend.application.dto.AssignmentResponsivaResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AssignmentResponsivaService {
    AssignmentResponsivaResponseDTO create(AssignmentResponsivaRequestDTO dto, MultipartFile pdf, MultipartFile photo);
    AssignmentResponsivaResponseDTO findById(UUID id);
    List<AssignmentResponsivaResponseDTO> findAll();
}