CREATE TABLE tipo_grupo (
	id BIGINT(20) PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	CONSTRAINT unique_nome UNIQUE (nome)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tipo_grupo (id, nome) values (1, 'Total');
INSERT INTO tipo_grupo (id, nome) values (2, 'Subtotal');
INSERT INTO tipo_grupo (id, nome) values (3, 'Cadastrado pelo usuário');