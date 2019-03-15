CREATE TABLE obra (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(200) NOT NULL,
	id_construtora BIGINT(20) NOT NULL,
	id_usuario_responsavel BIGINT(20) NOT NULL,
	ativo BOOLEAN NOT NULL,
	FOREIGN KEY (id_construtora) REFERENCES construtora(id),
	FOREIGN KEY (id_usuario_responsavel) REFERENCES usuario(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO obra (nome, id_construtora, id_usuario_responsavel, ativo) values ('Residencial Compasso do Sol - São José/SC', 1, 1, true);