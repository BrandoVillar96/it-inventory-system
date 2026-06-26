CREATE TABLE branches (
                          id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          name        VARCHAR(100) NOT NULL,
                          location    VARCHAR(255),
                          active      BOOLEAN DEFAULT TRUE,
                          created_at  TIMESTAMP DEFAULT NOW(),
                          updated_at  TIMESTAMP DEFAULT NOW()
);

CREATE TABLE persons (
                         id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         full_name   VARCHAR(150) NOT NULL,
                         position    VARCHAR(100),
                         email       VARCHAR(100) UNIQUE NOT NULL,
                         branch_id   UUID REFERENCES branches(id),
                         active      BOOLEAN DEFAULT TRUE,
                         created_at  TIMESTAMP DEFAULT NOW()
);

CREATE TABLE equipment (
                           id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           type             VARCHAR(50) NOT NULL,
                           brand            VARCHAR(100),
                           model            VARCHAR(100),
                           serial_number    VARCHAR(100) UNIQUE,
                           status           VARCHAR(50) DEFAULT 'ACTIVE',
                           branch_id        UUID REFERENCES branches(id),
                           assigned_to      UUID REFERENCES persons(id),
                           delivery_date    DATE,
                           last_maintenance DATE,
                           notes            TEXT,
                           created_at       TIMESTAMP DEFAULT NOW(),
                           updated_at       TIMESTAMP DEFAULT NOW()
);

CREATE TABLE supplies (
                          id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          type        VARCHAR(50) NOT NULL,
                          brand       VARCHAR(100),
                          quantity    INTEGER DEFAULT 0,
                          min_stock   INTEGER DEFAULT 5,
                          branch_id   UUID REFERENCES branches(id),
                          created_at  TIMESTAMP DEFAULT NOW()
);