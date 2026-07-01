package com.itinventory.backend.application.service;

import com.itinventory.backend.application.dto.AssignmentResponsivaRequestDTO;
import com.itinventory.backend.application.dto.AssignmentResponsivaResponseDTO;
import com.itinventory.backend.domain.entity.AssignmentResponsiva;
import com.itinventory.backend.domain.entity.Branch;
import com.itinventory.backend.domain.entity.Equipment;
import com.itinventory.backend.domain.entity.Person;
import com.itinventory.backend.domain.repository.AssignmentResponsivaRepository;
import com.itinventory.backend.domain.repository.BranchRepository;
import com.itinventory.backend.domain.repository.EquipmentRepository;
import com.itinventory.backend.domain.repository.PersonRepository;
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
public class AssignmentResponsivaServiceImpl implements AssignmentResponsivaService {

    private final AssignmentResponsivaRepository responsivaRepository;
    private final PersonRepository personRepository;
    private final EquipmentRepository equipmentRepository;
    private final BranchRepository branchRepository;
    private final FileStorageService fileStorageService;
    private final FolioGenerator folioGenerator;

    @Override
    public AssignmentResponsivaResponseDTO create(AssignmentResponsivaRequestDTO dto,
                                                  MultipartFile pdf, MultipartFile photo) {
        Person person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        // Genera folio único
        String folio;
        do {
            folio = folioGenerator.generate("RA");
        } while (responsivaRepository.existsByFolio(folio));

        // Guarda archivos
        String pdfPath = fileStorageService.store(pdf, "assignments");
        String photoPath = fileStorageService.store(photo, "assignments");

        AssignmentResponsiva responsiva = AssignmentResponsiva.builder()
                .folio(folio)
                .person(person)
                .equipment(equipment)
                .branch(branch)
                .pdfPath(pdfPath)
                .photoPath(photoPath)
                .observations(dto.getObservations())
                .build();

        return toDTO(responsivaRepository.save(responsiva));
    }

    @Override
    public AssignmentResponsivaResponseDTO findById(UUID id) {
        return toDTO(responsivaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsiva no encontrada")));
    }

    @Override
    public List<AssignmentResponsivaResponseDTO> findAll() {
        return responsivaRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AssignmentResponsivaResponseDTO toDTO(AssignmentResponsiva r) {
        AssignmentResponsivaResponseDTO dto = new AssignmentResponsivaResponseDTO();
        dto.setId(r.getId());
        dto.setFolio(r.getFolio());
        if (r.getPerson() != null) {
            dto.setPersonId(r.getPerson().getId());
            dto.setPersonName(r.getPerson().getFullName());
        }
        if (r.getEquipment() != null) {
            dto.setEquipmentId(r.getEquipment().getId());
            dto.setEquipmentInfo(r.getEquipment().getBrand() + " " + r.getEquipment().getModel());
        }
        if (r.getBranch() != null) {
            dto.setBranchId(r.getBranch().getId());
            dto.setBranchName(r.getBranch().getName());
        }
        dto.setPhotoPath(r.getPhotoPath());
        dto.setPdfPath(r.getPdfPath());
        dto.setObservations(r.getObservations());
        dto.setCreatedAt(r.getCreatedAt());
        return dto;
    }
}