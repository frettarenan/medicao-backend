package com.br.projetomedicao.medicaobackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.AlgaworksPessoa;

public interface AlgaworksPessoaRepository extends JpaRepository<AlgaworksPessoa, Long> {

	public Page<AlgaworksPessoa> findByNomeContaining(String nome, Pageable pageable);
	
}