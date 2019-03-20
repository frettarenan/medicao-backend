package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.ContratoServico;

public interface ServicoRepository extends JpaRepository<ContratoServico, Long> {

	List<ContratoServico> findByContrato(Contrato contrato);

}