package com.br.projetomedicao.medicaobackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Lancamento;
import com.br.projetomedicao.medicaobackend.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public List<Lancamento> salvar(List<Lancamento> lancamentos) {
		for (Lancamento lancamento : lancamentos) {
			lancamentoRepository.save(lancamento);
		}
		return lancamentos;
	}

}