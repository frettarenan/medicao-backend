CREATE TABLE medicao (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	id_contrato BIGINT(20) NOT NULL,
	FOREIGN KEY (id_contrato) REFERENCES contrato(id),
	CONSTRAINT unique_nome_medicao_por_contrato UNIQUE (nome, id_contrato)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO medicao (nome, id_contrato) values ('Medição 1ª semana de Janeiro de 2019', 1);