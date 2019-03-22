CREATE TABLE contrato_servico (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	id_contrato BIGINT(20) NOT NULL,
	FOREIGN KEY (id_contrato) REFERENCES contrato(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO contrato_servico (nome, id_contrato) values ('MO Cortina de contenção', 1);
INSERT INTO contrato_servico (nome, id_contrato) values ('MO Blocos de fundação e vigas baldrame', 1);
INSERT INTO contrato_servico (nome, id_contrato) values ('MO Recuperação de cubetas', 1);