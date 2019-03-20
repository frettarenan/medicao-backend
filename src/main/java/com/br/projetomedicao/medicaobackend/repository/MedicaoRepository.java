package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.ContratoMedicao;

public interface MedicaoRepository extends JpaRepository<ContratoMedicao, Long> {

	public List<ContratoMedicao> findByContrato(Contrato contrato);
	
}