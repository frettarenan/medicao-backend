package com.br.projetomedicao.medicaobackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Medicao;
import com.br.projetomedicao.medicaobackend.repository.MedicaoRepository;

@Service
public class MedicaoService {
	
	@Autowired
	private MedicaoRepository medicaoRepository;
	
	public Medicao salvar(Medicao medicao) {
		return medicaoRepository.save(medicao);
	}

	public Medicao renomear(Long id, Medicao medicao) {
		Medicao medicaoBD = buscarMedicaoPeloId(id);
		medicaoBD.setNome(medicao.getNome());
		return medicaoRepository.save(medicaoBD);
	}

	public Medicao buscarMedicaoPeloId(Long id) {
		Optional<Medicao> medicaoBD = medicaoRepository.findById(id);
		if (!medicaoBD.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return medicaoBD.get();
	}
	
}