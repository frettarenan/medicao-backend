CREATE TABLE usuario (
	codigo BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
	codigo BIGINT(20) PRIMARY KEY,
	chave VARCHAR(50) NOT NULL,
	descricao VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao (
	codigo_usuario BIGINT(20) NOT NULL,
	codigo_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_usuario, codigo_permissao),
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario (codigo, nome, email, senha) values (1, 'Administrador', 'admin@projetomedicao.com.br', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO usuario (codigo, nome, email, senha) values (2, 'Maria Silva', 'maria@projetomedicao.com.br', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permissao (codigo, chave, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA', 'Permissão para cadastrar categorias');
INSERT INTO permissao (codigo, chave, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA', 'Permissão para pesquisar categorias');

INSERT INTO permissao (codigo, chave, descricao) values (3, 'ROLE_CADASTRAR_PESSOA', 'Permissão para cadastrar pessoas');
INSERT INTO permissao (codigo, chave, descricao) values (4, 'ROLE_REMOVER_PESSOA', 'Permissão para remover pessoas');
INSERT INTO permissao (codigo, chave, descricao) values (5, 'ROLE_PESQUISAR_PESSOA', 'Permissão para pesquisar pessoas');

INSERT INTO permissao (codigo, chave, descricao) values (6, 'ROLE_CADASTRAR_LANCAMENTO', 'Permissão para cadastrar lançamentos');
INSERT INTO permissao (codigo, chave, descricao) values (7, 'ROLE_REMOVER_LANCAMENTO', 'Permissão para remover lançamentos');
INSERT INTO permissao (codigo, chave, descricao) values (8, 'ROLE_PESQUISAR_LANCAMENTO', 'Permissão para pesquisar lançamentos');

INSERT INTO permissao (codigo, chave, descricao) values (9, 'ROLE_CADASTRAR_CONSTRUTORA', 'Permissão para cadastrar construtoras');
INSERT INTO permissao (codigo, chave, descricao) values (10, 'ROLE_REMOVER_CONSTRUTORA', 'Permissão para remover construtoras');
INSERT INTO permissao (codigo, chave, descricao) values (11, 'ROLE_PESQUISAR_CONSTRUTORA', 'Permissão para pesquisar construtoras');

-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 3);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 4);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 6);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 8);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 9);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 10);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 11);

-- maria
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 8);
