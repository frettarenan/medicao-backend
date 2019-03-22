package com.br.projetomedicao.medicaobackend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class LancamentoId implements Serializable {
	
	private static final long serialVersionUID = -4688870542687129602L;
	
	@Column(name = "id_grupo")
	private Long idGrupo;
	
	@Column(name = "id_servico")
	private Long idServico;
	
	@Column(name = "id_medicao")
	private Long idMedicao;
	
}