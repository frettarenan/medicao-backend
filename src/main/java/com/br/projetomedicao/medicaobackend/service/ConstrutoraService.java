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
		Construtora construtoraSalva = buscarConstrutoraPeloId(id);
		BeanUtils.copyProperties(construtora, construtoraSalva, "id");
		return construtoraRepository.save(construtoraSalva);
	}

	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Construtora construtoraSalva = buscarConstrutoraPeloId(id);
		construtoraSalva.setAtivo(ativo);
		construtoraRepository.save(construtoraSalva);
	}
	
	public Construtora buscarConstrutoraPeloId(Long id) {
		Optional<Construtora> construtoraSalva = construtoraRepository.findById(id);
		if (!construtoraSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return construtoraSalva.get();
	}
	
}