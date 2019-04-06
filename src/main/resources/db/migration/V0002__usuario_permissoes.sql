CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	id_construtora BIGINT(20),
	telefone VARCHAR(20),	
	email VARCHAR(100) NOT NULL,
	senha VARCHAR(200) NOT NULL,
	ativo BOOLEAN NOT NULL,
  	administrador BOOLEAN NOT NULL,
  	CONSTRAINT unique_email_usuario UNIQUE (email)
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

INSERT INTO usuario (nome, id_construtora, telefone, email, senha, ativo, administrador) values ('Administrador', NULL, NULL, 'admin', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.', 1, 1);
INSERT INTO usuario (nome, id_construtora, telefone, email, senha, ativo, administrador) values ('Usuário de Teste', 1, NULL, 'teste', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.', 1, 0);

INSERT INTO permissao (id, chave, descricao) values (1, 'ROLE_PESQUISAR_CONSTRUTORA', 'Permissão para pesquisar construtoras');
INSERT INTO permissao (id, chave, descricao) values (2, 'ROLE_CADASTRAR_CONSTRUTORA', 'Permissão para cadastrar construtoras');
INSERT INTO permissao (id, chave, descricao) values (3, 'ROLE_REMOVER_CONSTRUTORA', 'Permissão para remover construtoras');

INSERT INTO permissao (id, chave, descricao) values (4, 'ROLE_PESQUISAR_USUARIO', 'Permissão para pesquisar usuários');
INSERT INTO permissao (id, chave, descricao) values (5, 'ROLE_CADASTRAR_USUARIO', 'Permissão para cadastrar usuários');
INSERT INTO permissao (id, chave, descricao) values (6, 'ROLE_REMOVER_USUARIO', 'Permissão para remover usuários');

INSERT INTO permissao (id, chave, descricao) values (7, 'ROLE_PESQUISAR_OBRA', 'Permissão para pesquisar obras');
INSERT INTO permissao (id, chave, descricao) values (8, 'ROLE_CADASTRAR_OBRA', 'Permissão para cadastrar obras');
INSERT INTO permissao (id, chave, descricao) values (9, 'ROLE_REMOVER_OBRA', 'Permissão para remover obras');

INSERT INTO permissao (id, chave, descricao) values (10, 'ROLE_CADASTRAR_GRUPO', 'Permissão para cadastrar grupos');
INSERT INTO permissao (id, chave, descricao) values (11, 'ROLE_REMOVER_GRUPO', 'Permissão para remover grupos');

INSERT INTO permissao (id, chave, descricao) values (12, 'ROLE_PESQUISAR_CONTRATO', 'Permissão para pesquisar contratos');
INSERT INTO permissao (id, chave, descricao) values (13, 'ROLE_CADASTRAR_CONTRATO', 'Permissão para cadastrar contratos');
INSERT INTO permissao (id, chave, descricao) values (14, 'ROLE_REMOVER_CONTRATO', 'Permissão para remover contratos');

INSERT INTO permissao (id, chave, descricao) values (15, 'ROLE_CADASTRAR_SERVICO', 'Permissão para cadastrar serviços');
INSERT INTO permissao (id, chave, descricao) values (16, 'ROLE_REMOVER_SERVICO', 'Permissão para remover serviços');

INSERT INTO permissao (id, chave, descricao) values (17, 'ROLE_PESQUISAR_MEDICAO', 'Permissão para pesquisar medições');
INSERT INTO permissao (id, chave, descricao) values (18, 'ROLE_CADASTRAR_MEDICAO', 'Permissão para cadastrar medições');
INSERT INTO permissao (id, chave, descricao) values (19, 'ROLE_REMOVER_MEDICAO', 'Permissão para remover medições');
INSERT INTO permissao (id, chave, descricao) values (20, 'ROLE_ADMINISTRAR_MEDICAO', 'Permissão para permitir cadastrar CUB e Quantidade da Matriz de Medição');

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
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 16);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 17);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 18);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 19);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 20);

-- Permissões do usuário de teste
--INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 1); Permissão removida para este usuário (somente administrador)
--INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 2); Permissão removida para este usuário (somente administrador)
--INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 3); Permissão removida para este usuário (somente administrador)
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
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 15);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 16);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 17);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 18);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 19);
--INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 20); Permissão removida para este usuário (somente administrador)