package com.br.projetomedicao.medicaobackend.model;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "lancamento")
public class Lancamento {
	
	@EmbeddedId
	private LancamentoId id;
	
	@ManyToOne
	@JoinColumn(name = "id_grupo", insertable=false, updatable=false)
	private Grupo grupo;
	
	@ManyToOne
	@JoinColumn(name = "id_servico", insertable=false, updatable=false)
	private Servico servico;
	
	@ManyToOne
	@JoinColumn(name = "id_medicao", insertable=false, updatable=false)
	private Medicao medicao;
	
	private BigDecimal valorQuantidade;
	private BigDecimal valorCub;
	private BigDecimal valorPercentual;
	
}