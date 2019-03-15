package com.br.projetomedicao.medicaobackend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.br.projetomedicao.medicaobackend.model.AlgaworksTipoLancamento;

public class AlgaworksLancamentoEstatisticaDia {
	
	private AlgaworksTipoLancamento tipo;
	
	private LocalDate dia;
	
	private BigDecimal total;

	public AlgaworksLancamentoEstatisticaDia(AlgaworksTipoLancamento tipo, LocalDate dia, BigDecimal total) {
		this.tipo = tipo;
		this.dia = dia;
		this.total = total;
	}

	public AlgaworksTipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(AlgaworksTipoLancamento tipo) {
		this.tipo = tipo;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}