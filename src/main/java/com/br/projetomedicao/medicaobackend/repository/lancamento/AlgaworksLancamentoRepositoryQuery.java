package com.br.projetomedicao.medicaobackend.repository.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.projetomedicao.medicaobackend.dto.AlgaworksLancamentoEstatisticaCategoria;
import com.br.projetomedicao.medicaobackend.dto.AlgaworksLancamentoEstatisticaDia;
import com.br.projetomedicao.medicaobackend.dto.AlgaworksLancamentoEstatisticaPessoa;
import com.br.projetomedicao.medicaobackend.model.AlgaworksLancamento;
import com.br.projetomedicao.medicaobackend.repository.filter.AlgaworksLancamentoFilter;
import com.br.projetomedicao.medicaobackend.repository.projection.AlgaworksResumoLancamento;

public interface AlgaworksLancamentoRepositoryQuery {
	
	public List<AlgaworksLancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim);
	public List<AlgaworksLancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	public List<AlgaworksLancamentoEstatisticaDia> porDia(LocalDate mesReferencia);

	public Page<AlgaworksLancamento> filtrar(AlgaworksLancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<AlgaworksResumoLancamento> resumir(AlgaworksLancamentoFilter lancamentoFilter, Pageable pageable);
	
}