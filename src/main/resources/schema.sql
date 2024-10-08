CREATE TABLE IF NOT EXISTS usuario (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS contato (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       nome VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    endereco VARCHAR(255),
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS telefone (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        numero VARCHAR(20) NOT NULL,
    contato_id BIGINT,
    FOREIGN KEY (contato_id) REFERENCES contato(id) ON DELETE CASCADE
    );
