package com.br.projetomedicao.medicaobackend.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Medicao;
import com.br.projetomedicao.medicaobackend.repository.MedicaoRepository;

@Service
public class MedicaoService {
	
	@Autowired
	private MedicaoRepository medicaoRepository;
	
	@Autowired
	private ContratoService contratoService;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private SegurancaService segurancaService;
	
	public List<Medicao> listarMedicaoComStatusAtivoPorContrato(Long idContrato) {
		Contrato contratoBD = contratoService.buscarContratoPeloId(idContrato);
		segurancaService.validaPermissaoAcessoPorConstrutora(contratoBD.getObra().getConstrutora().getId());
		return medicaoRepository.findByContrato(contratoBD);
	}
	
	public Medicao buscarMedicaoPeloId(Long idMedicao) {
		Optional<Medicao> medicaoBD = medicaoRepository.findById(idMedicao);
		if (!medicaoBD.isPresent())
			throw new EmptyResultDataAccessException(1);
		segurancaService.validaPermissaoAcessoPorConstrutora(medicaoBD.get().getContrato().getObra().getConstrutora().getId());
		return medicaoBD.get();
	}
	
	public Medicao salvar(Medicao medicao) {
		Contrato contratoBD = contratoService.buscarContratoPeloId(medicao.getContrato().getId());
		segurancaService.validaPermissaoAcessoPorConstrutora(contratoBD.getObra().getConstrutora().getId());
		medicao.setContrato(contratoBD);
		return medicaoRepository.save(medicao);
	}
	
	public Medicao salvarComo(Long idMedicao, String nome) {
		Medicao medicaoOrigem = buscarMedicaoPeloId(idMedicao);
		Medicao medicaoDestino = SerializationUtils.clone(medicaoOrigem);
		medicaoDestino.setId(null);
		medicaoDestino.setNome(nome);
		medicaoDestino = medicaoRepository.save(medicaoDestino);
		lancamentoService.salvarLancamentosComo(medicaoOrigem, medicaoDestino);
		return medicaoDestino;
	}

	public Medicao renomear(Long idMedicao, String nome) {
		Medicao medicaoBD = buscarMedicaoPeloId(idMedicao);
		medicaoBD.setNome(nome);
		return medicaoRepository.save(medicaoBD);
	}
	
}