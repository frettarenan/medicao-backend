package com.br.projetomedicao.medicaobackend.enums;

public enum MessagesPropertyEnum {
	
	ERRO__COMUNICACAO("erro.comunicacao"),
	ERRO__REGISTRO_NAO_EXISTE("erro.registro-nao-existe"),
	ERRO__INTEGRIDADE_BANCO_DADOS("erro.integridade-banco-dados"),
	ERRO__EXISTEM_REGISTROS_DEPENDENTES("erro.existem-registros-dependentes"),
	ERRO__DUPLICIDADE_BANCO_DADOS("erro.duplicidade-banco-dados"),
	
	// UNIQUE CONSTRAINTS
	ERRO__UNIQUE_CNPJ_CONSTRUTORA("erro.unique_cnpj_construtora"),
	ERRO__UNIQUE_EMAIL_USUARIO("erro.unique_email_usuario"),
	ERRO__UNIQUE_NOME_OBRA_POR_CONSTRUTORA("erro.unique_nome_obra_por_construtora"),
	ERRO__UNIQUE_NOME_TIPO_GRUPO("erro.unique_nome_tipo_grupo"),
	ERRO__UNIQUE_NOME_GRUPO_POR_OBRA("erro.unique_nome_grupo_por_obra"),
	ERRO__UNIQUE_ORDEM_GRUPO_POR_OBRA("erro.unique_ordem_grupo_por_obra"),
	ERRO__UNIQUE_DESCRICAO_CONTRATO_POR_OBRA("erro.unique_descricao_contrato_por_obra"),
	ERRO__UNIQUE_NUMERO_CONTRATO_POR_OBRA("erro.unique_numero_contrato_por_obra"),
	ERRO__UNIQUE_SIGLA_UNIDADE_MEDIDA("erro.unique_sigla_unidade_medida"),
	ERRO__UNIQUE_NOME_UNIDADE_MEDIDA("erro.unique_nome_unidade_medida"),
	ERRO__UNIQUE_NOME_SERVICO_POR_CONTRATO("erro.unique_nome_servico_por_contrato"),
	ERRO__UNIQUE_ORDEM_SERVICO_POR_CONTRATO("erro.unique_ordem_servico_por_contrato"),
	ERRO__UNIQUE_NOME_MEDICAO_POR_CONTRATO("erro.unique_nome_medicao_por_contrato");
	
	private String key;
	
	MessagesPropertyEnum(String key) {
        this.key = key;
    }

	public String getKey() {
		return key;
	}
	
}