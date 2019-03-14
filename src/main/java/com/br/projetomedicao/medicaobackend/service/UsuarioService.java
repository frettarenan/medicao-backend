package com.br.projetomedicao.medicaobackend.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Usuario;
import com.br.projetomedicao.medicaobackend.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public Usuario atualizar(Long id, Usuario usuario) {
		Usuario usuarioBD = buscarUsuarioPeloId(id);
		BeanUtils.copyProperties(usuario, usuarioBD, "id");
		return usuarioRepository.save(usuarioBD);
	}

	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Usuario usuarioBD = buscarUsuarioPeloId(id);
		usuarioBD.setAtivo(ativo);
		usuarioRepository.save(usuarioBD);
	}
	
	public Usuario buscarUsuarioPeloId(Long id) {
		Optional<Usuario> usuarioBD = usuarioRepository.findById(id);
		if (!usuarioBD.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuarioBD.get();
	}
	
}