package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Medicao;

public interface MedicaoRepository extends JpaRepository<Medicao, Long> {

	public List<Medicao> findByContrato(Contrato contrato);
	
}