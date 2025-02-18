CREATE DATABASE IF NOT EXISTS powerhr;
USE powerhr;

CREATE TABLE IF NOT EXISTS entreprise (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    secteur VARCHAR(50),
    matricule_fiscale VARCHAR(50) UNIQUE
); 