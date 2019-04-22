package com.br.projetomedicao.medicaobackend.dto;

import com.br.projetomedicao.medicaobackend.model.Lancamento;
import com.br.projetomedicao.medicaobackend.model.Servico;

import lombok.Data;

@Data
public class RelatorioMedicaoDTO {

	private Servico servico;
	
	private Lancamento lancamentoSubTotal;
	private Lancamento lancamentoSelecionado;
	private Lancamento lancamentoAnterior;
	
	
}