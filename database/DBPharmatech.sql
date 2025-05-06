CREATE DATABASE DBPharmatech;

USE DBPharmatech;

CREATE TABLE tbCliente (
    CPF VARCHAR(14) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    senha VARCHAR(50) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    endereco VARCHAR(100) NOT NULL,
    foto VARCHAR(200) NOT NULL
);

SELECT * FROM tbCliente;
