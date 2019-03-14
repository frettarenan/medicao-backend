package com.br.projetomedicao.medicaobackend.repository.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.projetomedicao.medicaobackend.model.Usuario;

public interface UsuarioRepositoryQuery {
	
	public Page<Usuario> pesquisar(String nome, String email, Long idConstrutora, Pageable pageable);
	
}