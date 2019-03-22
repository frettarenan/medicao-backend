CREATE TABLE lancamento (
	id_obra_grupo BIGINT(20) NOT NULL,
	id_contrato_servico BIGINT(20) NOT NULL,
	id_contrato_medicao BIGINT(20) NOT NULL,
	valor_quantidade DOUBLE,
	valor_unidade_medida DOUBLE,
	valor_percentual DOUBLE NOT NULL,
	PRIMARY KEY (id_obra_grupo, id_contrato_servico, id_contrato_medicao),
	FOREIGN KEY (id_obra_grupo) REFERENCES obra_grupo(id),
	FOREIGN KEY (id_contrato_servico) REFERENCES contrato_servico(id),
	FOREIGN KEY (id_contrato_medicao) REFERENCES contrato_medicao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (1, 1, 1, NULL, NULL, 0);
--INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (2, 2, 1, NULL, NULL, 0);
--INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (4, 3, 1, NULL, NULL, 0);
--INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (5, 4, 1, NULL, NULL, 0);
--INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (7, 5, 1, NULL, NULL, 0);
--INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (8, 6, 1, NULL, NULL, 0);
--INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (9, 7, 1, NULL, NULL, 0);

INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (1, 1, 1, 447.31, NULL, 100);
INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (1, 2, 1, 1201.17, NULL, 100);
INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (1, 3, 1, 938.75, NULL, 100);

INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (2, 1, 1, 111.83, NULL, 75);
INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (2, 2, 1, 133.46, NULL, 60);
INSERT INTO lancamento (id_obra_grupo, id_contrato_servico, id_contrato_medicao, valor_quantidade, valor_unidade_medida, valor_percentual) values (2, 3, 1, 150, NULL, 0);