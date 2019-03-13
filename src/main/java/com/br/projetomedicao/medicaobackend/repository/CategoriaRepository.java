package com.br.projetomedicao.medicaobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}