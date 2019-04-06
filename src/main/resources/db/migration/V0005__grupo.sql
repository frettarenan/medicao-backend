CREATE TABLE grupo (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	id_tipo_grupo BIGINT(20) NOT NULL,  
	id_obra BIGINT(20) NOT NULL,
	ordem INT(3) NULL,
	FOREIGN KEY (id_tipo_grupo) REFERENCES tipo_grupo(id),
	FOREIGN KEY (id_obra) REFERENCES obra(id),
	CONSTRAINT unique_nome_grupo_por_obra UNIQUE (nome, id_obra),
	CONSTRAINT unique_ordem_grupo_por_obra UNIQUE (ordem, id_obra)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO grupo (nome, id_tipo_grupo, id_obra, ordem) values ('TOTAL', 1, 1, 1);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra, ordem) values ('SUBTOTAL', 2, 1, 999);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra, ordem) values ('Subsolo', 3, 1, 2);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra, ordem) values ('Térreo', 3, 1, 3);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra, ordem) values ('Torre A / PVTO 1', 3, 1, 4);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra, ordem) values ('Torre A / PVTO 2', 3, 1, 5);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra, ordem) values ('Torre A / PVTO 3', 3, 1, 6);
INSERT INTO grupo (nome, id_tipo_grupo, id_obra, ordem) values ('Torre A / COBERT.', 3, 1, 7);