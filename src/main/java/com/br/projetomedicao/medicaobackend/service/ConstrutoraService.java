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

	public Construtora atualizar(Long codigo, Construtora construtora) {
		Construtora construtoraSalva = buscarConstrutoraPeloCodigo(codigo);
		BeanUtils.copyProperties(construtora, construtoraSalva, "codigo");
		return construtoraRepository.save(construtoraSalva);
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Construtora construtoraSalva = buscarConstrutoraPeloCodigo(codigo);
		construtoraSalva.setAtivo(ativo);
		construtoraRepository.save(construtoraSalva);
	}
	
	public Construtora buscarConstrutoraPeloCodigo(Long codigo) {
		Optional<Construtora> construtoraSalva = construtoraRepository.findById(codigo);
		if (!construtoraSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return construtoraSalva.get();
	}
	
}