package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Grupo;
import com.br.projetomedicao.medicaobackend.model.Obra;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	List<Grupo> findByObra(Obra obra);

}