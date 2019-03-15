package com.br.projetomedicao.medicaobackend.model;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "lancamento")
public class Lancamento {
	
	@EmbeddedId
	private LancamentoId id;
	
	@ManyToOne
	@JoinColumn(name = "id_obra_grupo", insertable=false, updatable=false)
	private ObraGrupo obraGrupo;
	
	@ManyToOne
	@JoinColumn(name = "id_contrato_servico", insertable=false, updatable=false)
	private ContratoServico contratoServico;
	
	@ManyToOne
	@JoinColumn(name = "id_contrato_medicao", insertable=false, updatable=false)
	private ContratoMedicao contratoMedicao;
	
	private BigDecimal valorQuantidade;
	private BigDecimal valorUnidadeMedida;
	
	@NotNull
	private BigDecimal valorPercentual;
	
}