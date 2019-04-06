package com.br.projetomedicao.medicaobackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.UnidadeMedida;
import com.br.projetomedicao.medicaobackend.repository.UnidadeMedidaRepository;

@Service
public class UnidadeMedidaService {
	
	@Autowired
	private UnidadeMedidaRepository unidadeMedidaRepository;
	
	public List<UnidadeMedida> listarUnidadesMedidasComStatusAtivo() {
		return unidadeMedidaRepository.findByAtivo(true);
	}

}