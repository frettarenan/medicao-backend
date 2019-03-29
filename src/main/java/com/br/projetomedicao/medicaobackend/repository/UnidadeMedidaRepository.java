package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.UnidadeMedida;

public interface UnidadeMedidaRepository extends JpaRepository<UnidadeMedida, Long> {

	public List<UnidadeMedida> findByAtivo(Boolean ativo);
	
}