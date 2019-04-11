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
import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.model.Usuario;
import com.br.projetomedicao.medicaobackend.repository.ObraRepository;

@Service
public class ObraService {
	
	@Autowired
	private ObraRepository obraRepository;
	
	@Autowired
	private ConstrutoraService construtoraService;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private SegurancaService segurancaService;
	
	public List<Obra> listarObrasComStatusAtivoPorConstrutora(Long idConstrutora) {
		Construtora construtoraBD = construtoraService.buscarConstrutoraPeloId(idConstrutora);
		return obraRepository.findByAtivoAndConstrutora(true, construtoraBD);
	}
	
	public Obra buscarObraPeloId(Long idObra) {
		Optional<Obra> obraBD = obraRepository.findById(idObra);
		if (!obraBD.isPresent())
			throw new EmptyResultDataAccessException(1);
		segurancaService.validaPermissaoAcessoPorConstrutora(obraBD.get().getConstrutora().getId());
		return obraBD.get();
	}
	
	public Page<Obra> pesquisar(String nome, Long idConstrutora, Pageable pageable) {
		segurancaService.validaPermissaoAcessoPorConstrutora(idConstrutora);
		return obraRepository.pesquisar(nome, idConstrutora, pageable);
	}
	
	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Obra obraBD = buscarObraPeloId(id);
		obraBD.setAtivo(ativo);
		obraRepository.save(obraBD);
	}
	
	public void remover(Long idObra) {
		Obra obraBD = buscarObraPeloId(idObra);
		grupoService.removerGruposDeSistemaDaObra(obraBD);
		obraRepository.delete(obraBD);
	}
	
	public Obra salvar(Obra obra) {
		validaPermissaoAcessoDoUsuarioResponsavelDaObra(obra);
		
		Obra obraBD = obraRepository.save(obra);
		grupoService.salvarGruposDeSistema(obraBD);
		return obraBD;
	}

	public Obra atualizar(Long idObra, Obra obra) {
		Obra obraBD = buscarObraPeloId(idObra);
		validaPermissaoAcessoDoUsuarioResponsavelDaObra(obra);
		
		BeanUtils.copyProperties(obra, obraBD, "id");
		return obraRepository.save(obraBD);
	}

	private void validaPermissaoAcessoDoUsuarioResponsavelDaObra(Obra obra) {
		Construtora construtoraBD = construtoraService.buscarConstrutoraPeloId(obra.getConstrutora().getId());
		Usuario usuarioResponsavelBD = usuarioService.buscarUsuarioPeloId(obra.getUsuarioResponsavel().getId());
		segurancaService.validaPermissaoAcessoDaConstrutoraDoUsuario(construtoraBD, usuarioResponsavelBD);
		obra.setConstrutora(construtoraBD);
		obra.setUsuarioResponsavel(usuarioResponsavelBD);
	}

}