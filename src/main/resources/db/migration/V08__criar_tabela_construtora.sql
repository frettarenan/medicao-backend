CREATE TABLE construtora (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	razao_social VARCHAR(200) NOT NULL,
	cnpj VARCHAR(18),
	ativo BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO construtora (razao_social, cnpj, ativo) values ('Construtora Habil Ltda', '03.972.341/0001-17', true);
