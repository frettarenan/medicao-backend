package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.AlgaworksCidade;

public interface CidadeRepository extends JpaRepository<AlgaworksCidade, Long> {
	
	List<AlgaworksCidade> findByEstadoId(Long idEstado);

}