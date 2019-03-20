package com.br.projetomedicao.medicaobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetomedicao.medicaobackend.model.ContratoMedicao;
import com.br.projetomedicao.medicaobackend.model.Lancamento;
import com.br.projetomedicao.medicaobackend.model.LancamentoId;

public interface LancamentoRepository extends JpaRepository<Lancamento, LancamentoId> {

	List<Lancamento> findByContratoMedicao(ContratoMedicao contratoMedicao);

}