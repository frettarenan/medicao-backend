package com.br.projetomedicao.medicaobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.AlgaworksEstado;

public interface EstadoRepository extends JpaRepository<AlgaworksEstado, Long> {

}