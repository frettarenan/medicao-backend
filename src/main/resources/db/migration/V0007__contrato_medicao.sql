CREATE TABLE contrato_medicao (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	id_contrato BIGINT(20) NOT NULL,
	FOREIGN KEY (id_contrato) REFERENCES contrato(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO contrato_medicao (nome, id_contrato) values ('Medição 1ª semana de Janeiro de 2019', 1);