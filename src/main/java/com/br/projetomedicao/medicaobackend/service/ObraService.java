package com.br.projetomedicao.medicaobackend.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.repository.ObraRepository;

@Service
public class ObraService {
	
	@Autowired
	private ObraRepository obraRepository;
	
	public Obra salvar(Obra obra) {
		return obraRepository.save(obra);
	}

	public Obra atualizar(Long id, Obra obra) {
		Obra obraBD = buscarObraPeloId(id);
		BeanUtils.copyProperties(obra, obraBD, "id");
		return obraRepository.save(obraBD);
	}

	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Obra obraBD = buscarObraPeloId(id);
		obraBD.setAtivo(ativo);
		obraRepository.save(obraBD);
	}
	
	public Obra buscarObraPeloId(Long id) {
		Optional<Obra> obraBD = obraRepository.findById(id);
		if (!obraBD.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return obraBD.get();
	}
	
}