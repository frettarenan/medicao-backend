package com.br.projetomedicao.medicaobackend.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.repository.ContratoRepository;

@Service
public class ContratoService {
	
	@Autowired
	private ContratoRepository contratoRepository;
	
	public Contrato salvar(Contrato contrato) {
		return contratoRepository.save(contrato);
	}

	public Contrato atualizar(Long id, Contrato contrato) {
		Contrato contratoBD = buscarContratoPeloId(id);
		BeanUtils.copyProperties(contrato, contratoBD, "id");
		return contratoRepository.save(contratoBD);
	}

	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Contrato contratoBD = buscarContratoPeloId(id);
		contratoBD.setAtivo(ativo);
		contratoRepository.save(contratoBD);
	}
	
	public Contrato buscarContratoPeloId(Long id) {
		Optional<Contrato> contratoBD = contratoRepository.findById(id);
		if (!contratoBD.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return contratoBD.get();
	}
	
}