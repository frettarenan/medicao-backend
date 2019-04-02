package com.br.projetomedicao.medicaobackend.service;

import java.util.Optional;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Medicao;
import com.br.projetomedicao.medicaobackend.repository.MedicaoRepository;

@Service
public class MedicaoService {
	
	@Autowired
	private MedicaoRepository medicaoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
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

	public Medicao salvarComo(Long id, String nome) {
		Optional<Medicao> medicao = medicaoRepository.findById(id);
		Medicao medicaoOrigem = medicao.get();
		Medicao medicaoDestino = SerializationUtils.clone(medicaoOrigem);
		medicaoDestino.setId(null);
		medicaoDestino.setNome(nome);
		medicaoDestino = medicaoRepository.save(medicaoDestino);
		lancamentoService.salvarComo(medicaoOrigem, medicaoDestino);
		return medicaoDestino;
	}
	
}