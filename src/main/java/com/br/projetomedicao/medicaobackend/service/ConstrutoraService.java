package com.br.projetomedicao.medicaobackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Construtora;
import com.br.projetomedicao.medicaobackend.repository.ConstrutoraRepository;

@Service
public class ConstrutoraService {
	
	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	@Autowired
	private SegurancaService segurancaService;
	
	public List<Construtora> listarConstrutorasComStatusAtivo() {
		segurancaService.validaPermissaoAcessoSomenteAdministrador();
		return construtoraRepository.findByAtivo(true);
	}
	
	public Construtora buscarConstrutoraPeloId(Long idConstrutora) {
		segurancaService.validaPermissaoAcessoPorConstrutora(idConstrutora);
		Optional<Construtora> construtoraBD = construtoraRepository.findById(idConstrutora);
		if (!construtoraBD.isPresent())
			throw new EmptyResultDataAccessException(1);
		return construtoraBD.get();
	}
	
	public Page<Construtora> pesquisar(String razaoSocial, Pageable pageable) {
		segurancaService.validaPermissaoAcessoSomenteAdministrador();
		return construtoraRepository.findByRazaoSocialContaining(razaoSocial, pageable);
	}
	
	public void atualizarPropriedadeAtivo(Long idConstrutora, Boolean ativo) {
		Construtora construtoraBD = buscarConstrutoraPeloId(idConstrutora);
		construtoraBD.setAtivo(ativo);
		construtoraRepository.save(construtoraBD);
	}
	
	public void remover(Long idConstrutora) {
		segurancaService.validaPermissaoAcessoSomenteAdministrador();
		construtoraRepository.deleteById(idConstrutora);		
	}
	
	public Construtora salvar(Construtora construtora) {
		segurancaService.validaPermissaoAcessoSomenteAdministrador();
		return construtoraRepository.save(construtora);
	}
	
	public Construtora atualizar(Long id, Construtora construtora) {
		Construtora construtoraBD = buscarConstrutoraPeloId(id);
		BeanUtils.copyProperties(construtora, construtoraBD, "id");
		return construtoraRepository.save(construtoraBD);
	}

}