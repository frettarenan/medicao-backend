package com.br.projetomedicao.medicaobackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Servico;
import com.br.projetomedicao.medicaobackend.repository.ServicoRepository;

@Service
public class ServicoService {

	@Autowired
	private ServicoRepository servicoRepository;
	
	private int getProximaOrdem(Contrato contrato) {
		Page<Servico> ultimaOrdem = servicoRepository.findByContratoOrderByOrdemDesc(contrato, PageRequest.of(0, 1));
		if (ultimaOrdem.getSize() > 0)
			return ultimaOrdem.getContent().get(0).getOrdem() +1;
		return 1;
	}

	public List<Servico> salvarNovosServicos(List<Servico> servicos) {
		int ordem = getProximaOrdem(servicos.get(0).getContrato());
		for (Servico servico : servicos) {
			servico.setOrdem(ordem);
			ordem++;
		}
		return servicoRepository.saveAll(servicos);
	}

	public List<Servico> salvarOrdenacao(List<Servico> servicos) {
		// Passo 1: Altera a ordenação para nulo
		for (Servico servico : servicos) {
			servico.setOrdem(null);
		}
		servicoRepository.saveAll(servicos);
		// Passo 2: Salva a ordenação correta
		int ordem = 1;
		for (Servico servico : servicos) {
			servico.setOrdem(ordem);
			ordem++;
		}
		return servicoRepository.saveAll(servicos);
	}
}