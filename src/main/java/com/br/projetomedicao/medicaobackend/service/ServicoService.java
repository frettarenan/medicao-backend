package com.br.projetomedicao.medicaobackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.exceptions.ViolacaoNosObjetosDaListaException;
import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Medicao;
import com.br.projetomedicao.medicaobackend.model.Servico;
import com.br.projetomedicao.medicaobackend.repository.ServicoRepository;

@Service
public class ServicoService {

	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private ContratoService contratoService;
	
	@Autowired
	private MedicaoService medicaoService;
	
	@Autowired
	private SegurancaService segurancaService;
	
	public List<Servico> listarServicosPorContrato(Long idContrato) {
		Contrato contratoBD = contratoService.buscarContratoPeloId(idContrato);
		return servicoRepository.findByContratoOrderByOrdemAsc(contratoBD);
	}
	
	public List<Servico> listarServicosPorMedicao(Long idMedicao) {
		Medicao medicaoBD = medicaoService.buscarMedicaoPeloId(idMedicao);
		return servicoRepository.findByContratoOrderByOrdemAsc(medicaoBD.getContrato());
	}
	
	private int getProximaOrdem(Contrato contrato) {
		Page<Servico> ultimaOrdem = servicoRepository.findByContratoOrderByOrdemDesc(contrato, PageRequest.of(0, 1));
		if (ultimaOrdem.getTotalElements() > 0)
			return ultimaOrdem.getContent().get(0).getOrdem() +1;
		return 1;
	}
	
	public List<Servico> salvarCadastroRapido(List<Servico> servicos) {
		if (servicos != null && servicos.size() > 0) {
			validaServicos(servicos);
			int ordem = getProximaOrdem(servicos.get(0).getContrato());
			for (Servico servico : servicos) {
				servico.setOrdem(ordem);
				ordem++;
			}
			return servicoRepository.saveAll(servicos);
		}
		return servicos;
	}
	
	private void validaServicos(List<Servico> servicos) {
		Long idContrato = null;
		for (Servico servico : servicos) {
			if (idContrato == null) {
				idContrato = servico.getContrato().getId();
			} else if (!idContrato.equals(servico.getContrato().getId())) {
				throw new ViolacaoNosObjetosDaListaException();
			}
		}
		if (idContrato != null) {
			Contrato contratoBD = contratoService.buscarContratoPeloId(idContrato);
			for (Servico servico : servicos) {
				servico.setContrato(contratoBD);
			}
		}
	}

	public List<Servico> salvarOrdenacao(List<Servico> servicos) {
		validaServicos(servicos);
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
	
	public Servico buscarServicoPeloId(Long idServico) {
		Optional<Servico> servicoBD = servicoRepository.findById(idServico);
		if (!servicoBD.isPresent())
			throw new EmptyResultDataAccessException(1);
		segurancaService.validaPermissaoAcessoPorConstrutora(servicoBD.get().getContrato().getObra().getConstrutora().getId());
		return servicoBD.get();
	}
	
	public void remover(Long idServico) {
		Servico servicoBD = buscarServicoPeloId(idServico);
		servicoRepository.delete(servicoBD);
	}

}