CREATE TABLE grupo (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	id_tipo_grupo BIGINT(20) NOT NULL,  
	id_obra BIGINT(20) NOT NULL,
	FOREIGN KEY (id_tipo_grupo) REFERENCES tipo_grupo(id),
	FOREIGN KEY (id_obra) REFERENCES obra(id),
	CONSTRAINT unique_nome_por_obra UNIQUE (nome, id_obra)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO grupo (nome, id_tipo_grupo, id_obra) values ('TOTAL', 1, 1);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra) values ('SUBTOTAL', 2, 1);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra) values ('Subsolo', 3, 1);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra) values ('Térreo', 3, 1);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra) values ('Torre A / PVTO 1', 3, 1);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra) values ('Torre A / PVTO 2', 3, 1);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra) values ('Torre A / PVTO 3', 3, 1);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra) values ('Torre A / COBERT.', 3, 1);