package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.model.ObraGrupo;

public interface GrupoRepository extends JpaRepository<ObraGrupo, Long> {

	List<ObraGrupo> findByObra(Obra obra);

}