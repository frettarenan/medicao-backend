CREATE TABLE contrato (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	numero VARCHAR(100),
	descricao VARCHAR(250) NOT NULL,
	id_obra BIGINT(20) NOT NULL,
	FOREIGN KEY (id_obra) REFERENCES obra(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO contrato (numero, descricao, id_obra) values ('0001/2019', 'Contrato de construção', 1);