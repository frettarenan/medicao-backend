package com.br.projetomedicao.medicaobackend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.exceptions.ViolacaoNosObjetosDaListaException;
import com.br.projetomedicao.medicaobackend.model.Lancamento;
import com.br.projetomedicao.medicaobackend.model.Medicao;
import com.br.projetomedicao.medicaobackend.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private MedicaoService medicaoService;
	
	@Autowired
	private SegurancaService segurancaService;

	public List<Lancamento> salvarTodos(List<Lancamento> lancamentos) {
		validaLancamentos(lancamentos);
		return lancamentoRepository.saveAll(lancamentos);
	}
	
	public void salvarLancamentosComo(Medicao medicaoOrigem, Medicao medicaoDestino) {
		List<Lancamento> lancamentosBD = lancamentoRepository.findByMedicao(medicaoOrigem);
		List<Lancamento> lancamentosNovos = new ArrayList<Lancamento>();
		Lancamento lancamentoClone;
		for (Lancamento lancamentoBD : lancamentosBD) {
			lancamentoClone = SerializationUtils.clone(lancamentoBD);
			lancamentoClone.getId().setIdMedicao(medicaoDestino.getId());
			lancamentoClone.setMedicao(medicaoDestino);
			lancamentosNovos.add(lancamentoClone);
		}
		lancamentoRepository.saveAll(lancamentosNovos);
	}
	
	private void validaLancamentos(List<Lancamento> lancamentos) {
		Long idMedicao = null;
		for (Lancamento lancamento : lancamentos) {
			if (idMedicao == null) {
				idMedicao = lancamento.getId().getIdMedicao();
			} else if (!idMedicao.equals(lancamento.getId().getIdMedicao())) {
				throw new ViolacaoNosObjetosDaListaException();
			}
		}
		if (idMedicao != null) {
			Medicao medicaoBD = medicaoService.buscarMedicaoPeloId(idMedicao);
			segurancaService.validaPermissaoAcessoPorConstrutora(medicaoBD.getContrato().getObra().getConstrutora().getId());
		}
	}

	public List<Lancamento> listarLancamentosPorMedicao(Long idMedicao) {
		Medicao medicaoBD = medicaoService.buscarMedicaoPeloId(idMedicao);
		segurancaService.validaPermissaoAcessoPorConstrutora(medicaoBD.getContrato().getObra().getConstrutora().getId());
		return lancamentoRepository.findByMedicao(medicaoBD);
	}

}