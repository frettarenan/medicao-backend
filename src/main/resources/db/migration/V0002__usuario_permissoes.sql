CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	id_construtora BIGINT(20),
	telefone VARCHAR(20),	
	email VARCHAR(100) NOT NULL,
	senha VARCHAR(200) NOT NULL,
	ativo BOOLEAN NOT NULL,
  	administrador BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
	id BIGINT(20) PRIMARY KEY,
	chave VARCHAR(50) NOT NULL,
	descricao VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao (
	id_usuario BIGINT(20) NOT NULL,
	id_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (id_usuario, id_permissao),
	FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	FOREIGN KEY (id_permissao) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario (id, nome, id_construtora, telefone, email, senha, ativo, administrador) values (1, 'Administrador', NULL, NULL, 'admin@projetomedicao.com.br', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.', 1, 1);

INSERT INTO permissao (id, chave, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA', 'Permissão para cadastrar categorias');
INSERT INTO permissao (id, chave, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA', 'Permissão para pesquisar categorias');

INSERT INTO permissao (id, chave, descricao) values (3, 'ROLE_CADASTRAR_PESSOA', 'Permissão para cadastrar pessoas');
INSERT INTO permissao (id, chave, descricao) values (4, 'ROLE_REMOVER_PESSOA', 'Permissão para remover pessoas');
INSERT INTO permissao (id, chave, descricao) values (5, 'ROLE_PESQUISAR_PESSOA', 'Permissão para pesquisar pessoas');

INSERT INTO permissao (id, chave, descricao) values (6, 'ROLE_CADASTRAR_LANCAMENTO', 'Permissão para cadastrar lançamentos');
INSERT INTO permissao (id, chave, descricao) values (7, 'ROLE_REMOVER_LANCAMENTO', 'Permissão para remover lançamentos');
INSERT INTO permissao (id, chave, descricao) values (8, 'ROLE_PESQUISAR_LANCAMENTO', 'Permissão para pesquisar lançamentos');

INSERT INTO permissao (id, chave, descricao) values (9, 'ROLE_CADASTRAR_CONSTRUTORA', 'Permissão para cadastrar construtoras');
INSERT INTO permissao (id, chave, descricao) values (10, 'ROLE_REMOVER_CONSTRUTORA', 'Permissão para remover construtoras');
INSERT INTO permissao (id, chave, descricao) values (11, 'ROLE_PESQUISAR_CONSTRUTORA', 'Permissão para pesquisar construtoras');

INSERT INTO permissao (id, chave, descricao) values (12, 'ROLE_CADASTRAR_USUARIO', 'Permissão para cadastrar usuários');
INSERT INTO permissao (id, chave, descricao) values (13, 'ROLE_REMOVER_USUARIO', 'Permissão para remover usuários');
INSERT INTO permissao (id, chave, descricao) values (14, 'ROLE_PESQUISAR_USUARIO', 'Permissão para pesquisar usuários');

-- Permissóes do usuário administrador
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 1);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 2);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 3);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 4);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 5);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 6);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 7);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 8);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 9);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 10);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 11);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 12);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 13);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 14);