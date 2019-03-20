package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Construtora;
import com.br.projetomedicao.medicaobackend.model.Obra;

public interface ObraRepository extends JpaRepository<Obra, Long> {

	public List<Obra> findByAtivoAndConstrutora(Boolean ativo, Construtora construtora);
	
}