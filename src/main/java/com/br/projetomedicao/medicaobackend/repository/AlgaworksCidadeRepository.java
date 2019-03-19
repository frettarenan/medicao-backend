package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.AlgaworksCidade;

public interface AlgaworksCidadeRepository extends JpaRepository<AlgaworksCidade, Long> {
	
	List<AlgaworksCidade> findByEstadoId(Long idEstado);

}