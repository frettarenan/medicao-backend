CREATE TABLE unidade_medida (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	sigla VARCHAR(20) NOT NULL,
	ativo BOOLEAN NOT NULL,
	CONSTRAINT unique_sigla UNIQUE (sigla),
	CONSTRAINT unique_nome UNIQUE (nome)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO unidade_medida (nome, sigla, ativo) values ('Metro', 'm', 1);