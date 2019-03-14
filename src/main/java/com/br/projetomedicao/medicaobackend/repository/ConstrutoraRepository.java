package com.br.projetomedicao.medicaobackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Construtora;

public interface ConstrutoraRepository extends JpaRepository<Construtora, Long> {

	public Page<Construtora> findByRazaoSocialContaining(String razaoSocial, Pageable pageable);
	
}