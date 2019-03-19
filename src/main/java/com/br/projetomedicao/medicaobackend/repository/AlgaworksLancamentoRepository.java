package com.br.projetomedicao.medicaobackend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.AlgaworksLancamento;
import com.br.projetomedicao.medicaobackend.repository.lancamento.AlgaworksLancamentoRepositoryQuery;

public interface AlgaworksLancamentoRepository extends JpaRepository<AlgaworksLancamento, Long>, AlgaworksLancamentoRepositoryQuery {
	
	List<AlgaworksLancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);

}