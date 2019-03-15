CREATE TABLE medicao (
	id_obra_grupo BIGINT(20) NOT NULL,
	id_contrato_servico BIGINT(20) NOT NULL,
	valor_quantidade DOUBLE,
	valor_unidade_medida DOUBLE,
	valor_percentual DOUBLE NOT NULL,
	FOREIGN KEY (id_obra_grupo) REFERENCES obra_grupo(id),
	FOREIGN KEY (id_contrato_servico) REFERENCES contrato_servico(id)	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO medicao (id_obra_grupo, id_contrato_servico, valor_quantidade, valor_unidade_medida, valor_percentual) values (1, 1, NULL, NULL, 0);
INSERT INTO medicao (id_obra_grupo, id_contrato_servico, valor_quantidade, valor_unidade_medida, valor_percentual) values (2, 2, NULL, NULL, 0);
INSERT INTO medicao (id_obra_grupo, id_contrato_servico, valor_quantidade, valor_unidade_medida, valor_percentual) values (4, 3, NULL, NULL, 0);
INSERT INTO medicao (id_obra_grupo, id_contrato_servico, valor_quantidade, valor_unidade_medida, valor_percentual) values (5, 4, NULL, NULL, 0);
INSERT INTO medicao (id_obra_grupo, id_contrato_servico, valor_quantidade, valor_unidade_medida, valor_percentual) values (7, 5, NULL, NULL, 0);
INSERT INTO medicao (id_obra_grupo, id_contrato_servico, valor_quantidade, valor_unidade_medida, valor_percentual) values (8, 6, NULL, NULL, 0);
INSERT INTO medicao (id_obra_grupo, id_contrato_servico, valor_quantidade, valor_unidade_medida, valor_percentual) values (9, 7, NULL, NULL, 0);