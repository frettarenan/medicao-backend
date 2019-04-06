CREATE TABLE servico (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	id_contrato BIGINT(20) NOT NULL,
	id_unidade_medida BIGINT(20) NOT NULL,
	ordem INT(3) NULL,
	FOREIGN KEY (id_contrato) REFERENCES contrato(id),
	FOREIGN KEY (id_unidade_medida) REFERENCES unidade_medida(id),
	CONSTRAINT unique_nome_servico_por_contrato UNIQUE (nome, id_contrato),
	CONSTRAINT unique_ordem_servico_por_contrato UNIQUE (ordem, id_contrato)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO servico (nome, id_contrato, id_unidade_medida, ordem) values ('MO Cortina de contenção', 1, 1, 1);
INSERT INTO servico (nome, id_contrato, id_unidade_medida, ordem) values ('MO Blocos de fundação e vigas baldrame', 1, 1, 2);
INSERT INTO servico (nome, id_contrato, id_unidade_medida, ordem) values ('MO Recuperação de cubetas', 1, 1, 3);