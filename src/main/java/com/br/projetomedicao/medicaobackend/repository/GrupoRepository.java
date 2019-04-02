package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Grupo;
import com.br.projetomedicao.medicaobackend.model.Obra;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	List<Grupo> findByObraOrderByOrdemAsc(Obra obra);
	
	Page<Grupo> findByObraOrderByOrdemDesc(Obra obra, Pageable pageable);

}