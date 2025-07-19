--Segunda Versao

CREATE TABLE user_tb (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255),
    type_user VARCHAR(20) NOT NULL,         -- Ex: 'PATIENT', 'PROFESSIONAL', 'ADMIN'
    birth_date DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    register DATETIME NOT NULL
);

CREATE TABLE pacient_tb (
    id_fk INT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE,        -- Formato XXX.XXX.XXX-XX
    health_insurance varchar(36),
    FOREIGN KEY (id_fk) REFERENCES user_tb(user_id)
);

CREATE TABLE professional_tb (
    id_fk INT PRIMARY KEY,
    bio VARCHAR(500),
    description VARCHAR(500),
    rate FLOAT CHECK (rate BETWEEN 0 AND 5),
    FOREIGN KEY (id_fk) REFERENCES user_tb(user_id)
);

CREATE TABLE appointment (
    appointment_id VARCHAR(36) PRIMARY KEY, -- UUID ou código alfanumérico
    professional_id_fk INT NOT NULL,
    pacient_id_fk INT NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME,
    status VARCHAR(20) NOT NULL DEFAULT 'Pending',
    obs VARCHAR(500),
    FOREIGN KEY (professional_id_fk) REFERENCES professional_tb(id_fk),
    FOREIGN KEY (pacient_id_fk) REFERENCES pacient_tb(id_fk)
);

CREATE TABLE credentials_tb (
    credentials_id INT auto_increment PRIMARY KEY,
    professional_id_fk INT NOT NULL,
    number VARCHAR(20) NOT NULL UNIQUE,      -- Pode conter letras
    fu CHAR(2) NOT NULL,                     -- Unidade federativa (UF)
    validation_date DATE NOT NULL,
    type VARCHAR(30) NOT NULL,               -- Ex: 'COREN', 'CREFITO'
    FOREIGN KEY (professional_id_fk) REFERENCES professional_tb(id_fk)
);

CREATE TABLE emails_tb (
    emails_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id_fk INT NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    type_email VARCHAR(20) DEFAULT 'personal',
    FOREIGN KEY (user_id_fk) REFERENCES user_tb(user_id)
);

CREATE TABLE phones_tb (
    phones_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id_fk INT NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    type_phone VARCHAR(20) DEFAULT 'cell',
    FOREIGN KEY (user_id_fk) REFERENCES user_tb(user_id)
);

CREATE TABLE address_tb (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id_fk INT NOT NULL,
    city VARCHAR(100) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    complement VARCHAR(100),
    number INT NOT NULL,
    street VARCHAR(100) NOT NULL,
    fu CHAR(2) NOT NULL,
    FOREIGN KEY (user_id_fk) REFERENCES user_tb(user_id)
);

CREATE TABLE availability_professional_tb (
    availability_id INT AUTO_INCREMENT PRIMARY KEY,
    professional_id_fk INT NOT NULL,
    week_day INT NOT NULL    --
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (professional_id_fk) REFERENCES professional_tb(id_fk)
);

CREATE TABLE assessment (
    professional_id_fk INT,
    pacient_id_fk INT,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    stars INT NOT NULL CHECK (stars BETWEEN 0 AND 5),
    PRIMARY KEY (professional_id_fk, pacient_id_fk),
    FOREIGN KEY (professional_id_fk) REFERENCES professional_tb(id_fk),
    FOREIGN KEY (pacient_id_fk) REFERENCES pacient_tb(id_fk)
);

CREATE TABLE history_tb (
    history_id INT AUTO_INCREMENT PRIMARY KEY,
    pacient_id_fk INT NOT NULL,
    history VARCHAR(500) NOT NULL,
    FOREIGN KEY (pacient_id_fk) REFERENCES pacient_tb(id_fk)
);