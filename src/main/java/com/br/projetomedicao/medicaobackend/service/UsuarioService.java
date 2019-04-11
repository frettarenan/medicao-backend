package com.br.projetomedicao.medicaobackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Construtora;
import com.br.projetomedicao.medicaobackend.model.Usuario;
import com.br.projetomedicao.medicaobackend.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ConstrutoraService construtoraService;
	
	@Autowired
	private SegurancaService segurancaService;
	
	public Usuario login(String email) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		if (!usuarioOptional.isPresent())
			throw new BadCredentialsException("Usuário inexistente ou senha inválida");
		Usuario usuario = usuarioOptional.get();
		if (!usuario.getAtivo() || (usuario.getConstrutora() != null && !usuario.getConstrutora().getAtivo()))
			throw new BadCredentialsException("Usuário inativo");
		return usuario;
	}
	
	public List<Usuario> listarUsuariosComStatusAtivoPorConstrutora(Long idConstrutora) {
		Construtora construtoraBD = construtoraService.buscarConstrutoraPeloId(idConstrutora);
		return usuarioRepository.findByAtivoAndConstrutora(true, construtoraBD);
	}
	
	public Usuario buscarUsuarioPeloId(Long idUsuario) {
		Optional<Usuario> usuarioBD = usuarioRepository.findById(idUsuario);
		if (!usuarioBD.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		segurancaService.validaPermissaoAcessoPorConstrutora(usuarioBD.get().getConstrutora().getId());
		return usuarioBD.get();
	}
	
	public Page<Usuario> pesquisar(String nome, String email, Long idConstrutora, Pageable pageable) {
		segurancaService.validaPermissaoAcessoPorConstrutora(idConstrutora);
		return usuarioRepository.pesquisar(nome, email, idConstrutora, pageable);
	}
	
	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Usuario usuarioBD = buscarUsuarioPeloId(id);
		usuarioBD.setAtivo(ativo);
		usuarioRepository.save(usuarioBD);
	}
	
	public void remover(Long idUsuario) {
		Usuario usuarioBD = buscarUsuarioPeloId(idUsuario);
		usuarioRepository.delete(usuarioBD);
	}
	
	public Usuario salvar(Usuario usuario) {
		Construtora construtoraBD = construtoraService.buscarConstrutoraPeloId(usuario.getConstrutora().getId());
		usuario.setConstrutora(construtoraBD);
		return usuarioRepository.save(usuario);
	}

	public Usuario atualizar(Long idUsuario, Usuario usuario) {
		Usuario usuarioBD = buscarUsuarioPeloId(idUsuario);
		BeanUtils.copyProperties(usuario, usuarioBD, "id");
		return usuarioRepository.save(usuarioBD);
	}
	
}