package com.br.projetomedicao.medicaobackend.dto;

import java.math.BigDecimal;

import com.br.projetomedicao.medicaobackend.model.AlgaworksPessoa;
import com.br.projetomedicao.medicaobackend.model.AlgaworksTipoLancamento;

public class AlgaworksLancamentoEstatisticaPessoa {
	
	private AlgaworksTipoLancamento tipo;
	
	private AlgaworksPessoa pessoa;
	
	private BigDecimal total;

	public AlgaworksLancamentoEstatisticaPessoa(AlgaworksTipoLancamento tipo, AlgaworksPessoa pessoa, BigDecimal total) {
		this.tipo = tipo;
		this.pessoa = pessoa;
		this.total = total;
	}

	public AlgaworksTipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(AlgaworksTipoLancamento tipo) {
		this.tipo = tipo;
	}

	public AlgaworksPessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(AlgaworksPessoa pessoa) {
		this.pessoa = pessoa;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}