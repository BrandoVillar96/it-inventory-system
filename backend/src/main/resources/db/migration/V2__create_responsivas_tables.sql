-- Responsiva de asignación de equipo
CREATE TABLE assignment_responsivas (
                                        id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                        folio         VARCHAR(50) UNIQUE NOT NULL,
                                        person_id     UUID REFERENCES persons(id),
                                        equipment_id  UUID REFERENCES equipment(id),
                                        branch_id     UUID REFERENCES branches(id),
                                        photo_path    VARCHAR(255),
                                        pdf_path      VARCHAR(255),
                                        observations  TEXT,
                                        created_at    TIMESTAMP DEFAULT NOW()
);

-- Responsiva de envío
CREATE TABLE shipment_responsivas (
                                      id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                      folio                 VARCHAR(50) UNIQUE NOT NULL,
                                      origin_branch_id      UUID REFERENCES branches(id),
                                      destination_branch_id UUID REFERENCES branches(id),
                                      ship_date             DATE,
                                      sender_name           VARCHAR(150),
                                      receiver_name         VARCHAR(150),
                                      barcode_folio         VARCHAR(50),
                                      pdf_path              VARCHAR(255),
                                      notes                 TEXT,
                                      created_at            TIMESTAMP DEFAULT NOW()
);