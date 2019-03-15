CREATE TABLE obra_grupo (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	id_obra BIGINT(20) NOT NULL,
	id_obra_grupo_pai BIGINT(20),
	FOREIGN KEY (id_obra) REFERENCES obra(id),
	FOREIGN KEY (id_obra_grupo_pai) REFERENCES obra_grupo(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO obra_grupo (nome, id_obra, id_obra_grupo_pai) values ('Subsolo', 1, NULL);
INSERT INTO obra_grupo (nome, id_obra, id_obra_grupo_pai) values ('Térreo', 1, NULL);
INSERT INTO obra_grupo (nome, id_obra, id_obra_grupo_pai) values ('Torre A', 1, NULL);
INSERT INTO obra_grupo (nome, id_obra, id_obra_grupo_pai) values ('PVTO 1', 1, 3);
INSERT INTO obra_grupo (nome, id_obra, id_obra_grupo_pai) values ('PVTO 2', 1, 3);
INSERT INTO obra_grupo (nome, id_obra, id_obra_grupo_pai) values ('Torre B', 1, NULL);
INSERT INTO obra_grupo (nome, id_obra, id_obra_grupo_pai) values ('PVTO 1', 1, 6);
INSERT INTO obra_grupo (nome, id_obra, id_obra_grupo_pai) values ('PVTO 2', 1, 6);
INSERT INTO obra_grupo (nome, id_obra, id_obra_grupo_pai) values ('PVTO 3', 1, 6);