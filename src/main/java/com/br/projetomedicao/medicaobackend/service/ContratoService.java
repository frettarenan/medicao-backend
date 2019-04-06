package com.br.projetomedicao.medicaobackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.repository.ContratoRepository;

@Service
public class ContratoService {
	
	@Autowired
	private ContratoRepository contratoRepository;
	
	@Autowired
	private ObraService obraService;
	
	@Autowired
	private SegurancaService segurancaService;
	
	public List<Contrato> listarContratoComStatusAtivoPorObra(Long idObra) {
		Obra obraBD = obraService.buscarObraPeloId(idObra);
		segurancaService.validaPermissaoAcessoPorConstrutora(obraBD.getConstrutora().getId());
		return contratoRepository.findByAtivoAndObra(true, obraBD);
	}
	
	public Contrato buscarContratoPeloId(Long idContrato) {
		Optional<Contrato> contratoBD = contratoRepository.findById(idContrato);
		if (!contratoBD.isPresent())
			throw new EmptyResultDataAccessException(1);
		segurancaService.validaPermissaoAcessoPorConstrutora(contratoBD.get().getObra().getConstrutora().getId());
		return contratoBD.get();
	}
	
	public Page<Contrato> pesquisar(String numero, String descricao, Long idConstrutora, Long idObra, Pageable pageable) {
		segurancaService.validaPermissaoAcessoPorConstrutora(idConstrutora);
		return contratoRepository.pesquisar(numero, descricao, idConstrutora, idObra, pageable);
	}
	
	public void atualizarPropriedadeAtivo(Long idContrato, Boolean ativo) {
		Contrato contratoBD = buscarContratoPeloId(idContrato);
		contratoBD.setAtivo(ativo);
		contratoRepository.save(contratoBD);
	}
	
	public void remover(Long idContrato) {
		Contrato contratoBD = buscarContratoPeloId(idContrato);
		contratoRepository.delete(contratoBD);
	}
	
	public Contrato salvar(Contrato contrato) {
		Obra obraBD = obraService.buscarObraPeloId(contrato.getObra().getId());
		segurancaService.validaPermissaoAcessoPorConstrutora(obraBD.getConstrutora().getId());
		contrato.setObra(obraBD);
		return contratoRepository.save(contrato);
	}

	public Contrato atualizar(Long idContrato, Contrato contrato) {
		Contrato contratoBD = buscarContratoPeloId(idContrato);
		BeanUtils.copyProperties(contrato, contratoBD, "id");
		return contratoRepository.save(contratoBD);
	}

}