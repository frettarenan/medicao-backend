package com.br.projetomedicao.medicaobackend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class LancamentoId implements Serializable {
	
	private static final long serialVersionUID = -4688870542687129602L;
	
	@Column(name = "id_obra_grupo")
	private Long idObraGrupo;
	
	@Column(name = "id_contrato_servico")
	private Long idContratoServico;
	
	@Column(name = "id_contrato_medicao")
	private Long idContratoMedicao;
	
}