package com.br.projetomedicao.medicaobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.AlgaworksCategoria;

public interface CategoriaRepository extends JpaRepository<AlgaworksCategoria, Long> {

}