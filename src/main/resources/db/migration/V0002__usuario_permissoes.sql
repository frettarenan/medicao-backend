CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
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

INSERT INTO usuario (nome, id_construtora, telefone, email, senha, ativo, administrador) values ('Administrador', NULL, NULL, 'admin@projetomedicao.com.br', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.', 1, 1);
INSERT INTO usuario (nome, id_construtora, telefone, email, senha, ativo, administrador) values ('Usuário de Teste', 1, NULL, 'usuarioteste@projetomedicao.com.br', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.', 1, 1);

INSERT INTO permissao (id, chave, descricao) values (1, 'ROLE_PESQUISAR_CONSTRUTORA', 'Permissão para pesquisar construtoras');
INSERT INTO permissao (id, chave, descricao) values (2, 'ROLE_CADASTRAR_CONSTRUTORA', 'Permissão para cadastrar construtoras');
INSERT INTO permissao (id, chave, descricao) values (3, 'ROLE_REMOVER_CONSTRUTORA', 'Permissão para remover construtoras');

INSERT INTO permissao (id, chave, descricao) values (4, 'ROLE_PESQUISAR_USUARIO', 'Permissão para pesquisar usuários');
INSERT INTO permissao (id, chave, descricao) values (5, 'ROLE_CADASTRAR_USUARIO', 'Permissão para cadastrar usuários');
INSERT INTO permissao (id, chave, descricao) values (6, 'ROLE_REMOVER_USUARIO', 'Permissão para remover usuários');

INSERT INTO permissao (id, chave, descricao) values (7, 'ROLE_PESQUISAR_OBRA', 'Permissão para pesquisar obras');
INSERT INTO permissao (id, chave, descricao) values (8, 'ROLE_CADASTRAR_OBRA', 'Permissão para cadastrar obras');
INSERT INTO permissao (id, chave, descricao) values (9, 'ROLE_REMOVER_OBRA', 'Permissão para remover obras');

INSERT INTO permissao (id, chave, descricao) values (10, 'ROLE_PESQUISAR_CONTRATO', 'Permissão para pesquisar contratos');
INSERT INTO permissao (id, chave, descricao) values (11, 'ROLE_CADASTRAR_CONTRATO', 'Permissão para cadastrar contratos');
INSERT INTO permissao (id, chave, descricao) values (12, 'ROLE_REMOVER_CONTRATO', 'Permissão para remover contratos');

INSERT INTO permissao (id, chave, descricao) values (13, 'ROLE_PESQUISAR_MATRIZ_MEDICAO', 'Permissão para pesquisar matriz medição');
INSERT INTO permissao (id, chave, descricao) values (14, 'ROLE_CADASTRAR_MATRIZ_MEDICAO', 'Permissão para cadastrar matriz medição');
INSERT INTO permissao (id, chave, descricao) values (15, 'ROLE_ADMINISTRAR_MATRIZ_MEDICAO', 'Permissão para administrar matriz medição, ou seja, permite cadastrar CUB e Quantidade da Matriz');

-- Permissões do usuário administrador
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
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 15);

-- Permissões do usuário de teste
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 1);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 2);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 3);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 4);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 5);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 6);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 7);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 8);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 9);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 10);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 11);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 12);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 13);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 14);
--INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 15); Permissão removida para este usuário (somente administrador)