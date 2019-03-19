package com.br.projetomedicao.medicaobackend.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.br.projetomedicao.medicaobackend.model.AlgaworksTipoLancamento;

import lombok.Data;

@Data
public class AlgaworksResumoLancamento {

	private Long id;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private AlgaworksTipoLancamento tipo;
	private String categoria;
	private String pessoa;
	
	public AlgaworksResumoLancamento(Long id, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			BigDecimal valor, AlgaworksTipoLancamento tipo, String categoria, String pessoa) {
		this.id = id;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}

}