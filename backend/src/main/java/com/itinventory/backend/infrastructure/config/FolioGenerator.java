package com.itinventory.backend.infrastructure.config;

import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class FolioGenerator {

    /**
     * Genera un folio con formato PREFIJO-AÑO-XXXXX
     * Ejemplo: RA-2026-04821 (Responsiva Asignación)
     *          RE-2026-09134 (Responsiva Envío)
     */
    public String generate(String prefix) {
        int year = Year.now().getValue();
        int random = ThreadLocalRandom.current().nextInt(10000, 100000);
        return String.format("%s-%d-%05d", prefix, year, random);
    }
}