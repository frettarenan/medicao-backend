CREATE TABLE contrato (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	numero VARCHAR(100),
	descricao VARCHAR(250) NOT NULL,
	id_obra BIGINT(20) NOT NULL,
	ativo BOOLEAN NOT NULL,
	FOREIGN KEY (id_obra) REFERENCES obra(id),
	CONSTRAINT unique_descricao_contrato_por_obra UNIQUE (descricao, id_obra),
	CONSTRAINT unique_numero_contrato_por_obra UNIQUE (numero, id_obra)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO contrato (numero, descricao, id_obra, ativo) values ('0001/2019', 'Contrato de construção', 1, 1);