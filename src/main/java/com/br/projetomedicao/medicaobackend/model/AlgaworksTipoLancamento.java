package com.br.projetomedicao.medicaobackend.model;

public enum AlgaworksTipoLancamento {

	RECEITA("Receita"),
	DESPESA("Despesa")
	
	;
	
	private final String descricao;
	
	AlgaworksTipoLancamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}	
}
