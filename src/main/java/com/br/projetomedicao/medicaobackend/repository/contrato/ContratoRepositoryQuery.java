package com.br.projetomedicao.medicaobackend.repository.contrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.projetomedicao.medicaobackend.model.Contrato;

public interface ContratoRepositoryQuery {
	
	public Page<Contrato> pesquisar(String numero, String descricao, Long idContrutora, Long idObra, Pageable pageable);
	
}