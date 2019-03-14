package com.br.projetomedicao.medicaobackend.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Construtora;
import com.br.projetomedicao.medicaobackend.repository.ConstrutoraRepository;

@Service
public class ConstrutoraService {
	
	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	public Construtora salvar(Construtora construtora) {
		return construtoraRepository.save(construtora);
	}

	public Construtora atualizar(Long id, Construtora construtora) {
		Construtora construtoraBD = buscarConstrutoraPeloId(id);
		BeanUtils.copyProperties(construtora, construtoraBD, "id");
		return construtoraRepository.save(construtoraBD);
	}

	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Construtora construtoraBD = buscarConstrutoraPeloId(id);
		construtoraBD.setAtivo(ativo);
		construtoraRepository.save(construtoraBD);
	}
	
	public Construtora buscarConstrutoraPeloId(Long id) {
		Optional<Construtora> construtoraBD = construtoraRepository.findById(id);
		if (!construtoraBD.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return construtoraBD.get();
	}
	
}