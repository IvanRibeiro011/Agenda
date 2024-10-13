CREATE TABLE IF NOT EXISTS usuario (
                                       id BIGSERIAL PRIMARY KEY,
                                       nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS contato (
                                       id BIGSERIAL PRIMARY KEY,
                                       nome VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    endereco VARCHAR(255),
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS telefone (
                                        id BIGSERIAL PRIMARY KEY,
                                        numero VARCHAR(20) NOT NULL,
    contato_id BIGINT,
    FOREIGN KEY (contato_id) REFERENCES contato(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS authToken (
                                         id BIGSERIAL PRIMARY KEY,
                                         token VARCHAR(512) NOT NULL,
    usuario_id BIGINT,
    expiracao TIMESTAMP NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
    );
