CREATE TABLE lancamento (
	id_grupo BIGINT(20) NOT NULL,
	id_servico BIGINT(20) NOT NULL,
	id_medicao BIGINT(20) NOT NULL,
	valor_quantidade DOUBLE,
	valor_cub DOUBLE,
	valor_percentual DOUBLE,
	FOREIGN KEY (id_grupo) REFERENCES grupo(id),
	FOREIGN KEY (id_servico) REFERENCES servico(id),
	FOREIGN KEY (id_medicao) REFERENCES medicao(id),
	PRIMARY KEY (id_grupo, id_servico, id_medicao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (1, 1, 1, NULL, 71.55, NULL);
INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (1, 2, 1, NULL, 162.50, NULL);
INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (1, 3, 1, NULL, 15.71, NULL);

INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (2, 1, 1, NULL, NULL, NULL);
INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (2, 2, 1, NULL, NULL, NULL);
INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (2, 3, 1, NULL, NULL, NULL);

INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (3, 1, 1, 447.31, NULL, 100);
INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (3, 2, 1, 1201.17, NULL, 100);
INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (3, 3, 1, 938.75, NULL, 100);

INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (4, 1, 1, 111.83, NULL, 75);
INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (4, 2, 1, 133.46, NULL, 60);
INSERT INTO lancamento (id_grupo, id_servico, id_medicao, valor_quantidade, valor_cub, valor_percentual) values (4, 3, 1, 150, NULL, 0);