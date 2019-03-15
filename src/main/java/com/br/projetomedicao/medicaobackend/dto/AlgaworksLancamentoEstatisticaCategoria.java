package com.br.projetomedicao.medicaobackend.dto;

import java.math.BigDecimal;

import com.br.projetomedicao.medicaobackend.model.AlgaworksCategoria;

public class AlgaworksLancamentoEstatisticaCategoria {
	
	private AlgaworksCategoria categoria;
	
	private BigDecimal total;

	public AlgaworksLancamentoEstatisticaCategoria(AlgaworksCategoria categoria, BigDecimal total) {
		this.categoria = categoria;
		this.total = total;
	}

	public AlgaworksCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(AlgaworksCategoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}