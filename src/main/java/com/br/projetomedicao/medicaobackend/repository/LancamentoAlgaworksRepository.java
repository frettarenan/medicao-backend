package com.br.projetomedicao.medicaobackend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.LancamentoAlgaworks;
import com.br.projetomedicao.medicaobackend.repository.lancamento.LancamentoAlgaworksRepositoryQuery;

public interface LancamentoAlgaworksRepository extends JpaRepository<LancamentoAlgaworks, Long>, LancamentoAlgaworksRepositoryQuery {
	
	List<LancamentoAlgaworks> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);

}