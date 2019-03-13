package com.br.projetomedicao.medicaobackend.repository.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.projetomedicao.medicaobackend.dto.LancamentoEstatisticaCategoria;
import com.br.projetomedicao.medicaobackend.dto.LancamentoEstatisticaDia;
import com.br.projetomedicao.medicaobackend.dto.LancamentoEstatisticaPessoa;
import com.br.projetomedicao.medicaobackend.model.Lancamento;
import com.br.projetomedicao.medicaobackend.repository.filter.LancamentoFilter;
import com.br.projetomedicao.medicaobackend.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {
	
	public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim);
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia);

	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}