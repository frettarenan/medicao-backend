package com.br.projetomedicao.medicaobackend.repository.obra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.projetomedicao.medicaobackend.model.Obra;

public interface ObraRepositoryQuery {
	
	public Page<Obra> pesquisar(String nome, Long idConstrutora, Pageable pageable);
	
}