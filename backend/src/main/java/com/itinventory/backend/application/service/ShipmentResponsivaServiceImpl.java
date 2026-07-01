package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.ShipmentResponsivaRequestDTO;
import com.itinventory.backend.application.dto.ShipmentResponsivaResponseDTO;
import com.itinventory.backend.domain.entity.Branch;
import com.itinventory.backend.domain.entity.ShipmentResponsiva;
import com.itinventory.backend.domain.repository.BranchRepository;
import com.itinventory.backend.domain.repository.ShipmentResponsivaRepository;
import com.itinventory.backend.infrastructure.config.FileStorageService;
import com.itinventory.backend.infrastructure.config.FolioGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentResponsivaServiceImpl implements ShipmentResponsivaService {

    private final ShipmentResponsivaRepository responsivaRepository;
    private final BranchRepository branchRepository;
    private final FileStorageService fileStorageService;
    private final FolioGenerator folioGenerator;

    @Override
    public ShipmentResponsivaResponseDTO create(ShipmentResponsivaRequestDTO dto, MultipartFile pdf) {
        Branch origin = branchRepository.findById(dto.getOriginBranchId())
                .orElseThrow(() -> new RuntimeException("Sucursal origen no encontrada"));
        Branch destination = branchRepository.findById(dto.getDestinationBranchId())
                .orElseThrow(() -> new RuntimeException("Sucursal destino no encontrada"));

        String folio;
        do {
            folio = folioGenerator.generate("RE");
        } while (responsivaRepository.existsByFolio(folio));

        String pdfPath = fileStorageService.store(pdf, "shipments");

        ShipmentResponsiva responsiva = ShipmentResponsiva.builder()
                .folio(folio)
                .originBranch(origin)
                .destinationBranch(destination)
                .shipDate(dto.getShipDate())
                .senderName(dto.getSenderName())
                .receiverName(dto.getReceiverName())
                .barcodeFolio(dto.getBarcodeFolio())
                .pdfPath(pdfPath)
                .notes(dto.getNotes())
                .build();

        return toDTO(responsivaRepository.save(responsiva));
    }

    @Override
    public ShipmentResponsivaResponseDTO findById(UUID id) {
        return toDTO(responsivaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsiva no encontrada")));
    }

    @Override
    public List<ShipmentResponsivaResponseDTO> findAll() {
        return responsivaRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ShipmentResponsivaResponseDTO toDTO(ShipmentResponsiva r) {
        ShipmentResponsivaResponseDTO dto = new ShipmentResponsivaResponseDTO();
        dto.setId(r.getId());
        dto.setFolio(r.getFolio());
        if (r.getOriginBranch() != null) {
            dto.setOriginBranchId(r.getOriginBranch().getId());
            dto.setOriginBranchName(r.getOriginBranch().getName());
        }
        if (r.getDestinationBranch() != null) {
            dto.setDestinationBranchId(r.getDestinationBranch().getId());
            dto.setDestinationBranchName(r.getDestinationBranch().getName());
        }
        dto.setShipDate(r.getShipDate());
        dto.setSenderName(r.getSenderName());
        dto.setReceiverName(r.getReceiverName());
        dto.setBarcodeFolio(r.getBarcodeFolio());
        dto.setPdfPath(r.getPdfPath());
        dto.setNotes(r.getNotes());
        dto.setCreatedAt(r.getCreatedAt());
        return dto;
    }
}