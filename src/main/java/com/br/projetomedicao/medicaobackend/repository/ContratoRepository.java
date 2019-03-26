package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Obra;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

	public List<Contrato> findByAtivoAndObra(Boolean ativo, Obra obra);
	
}