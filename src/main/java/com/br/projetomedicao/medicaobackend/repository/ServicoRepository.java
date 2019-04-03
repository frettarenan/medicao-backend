package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

	List<Servico> findByContratoOrderByOrdemAsc(Contrato contrato);
	
	Page<Servico> findByContratoOrderByOrdemDesc(Contrato contrato, Pageable pageable);

}