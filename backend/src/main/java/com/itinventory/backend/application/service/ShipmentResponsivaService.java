package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.ShipmentResponsivaRequestDTO;
import com.itinventory.backend.application.dto.ShipmentResponsivaResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ShipmentResponsivaService {
    ShipmentResponsivaResponseDTO create(ShipmentResponsivaRequestDTO dto, MultipartFile pdf);
    ShipmentResponsivaResponseDTO findById(UUID id);
    List<ShipmentResponsivaResponseDTO> findAll();
}