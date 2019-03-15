package com.br.projetomedicao.medicaobackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "obra_grupo")
public class ObraGrupo {
	
	@Id
	private Long id;
	
	@NotNull
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_obra")
	private Obra obra;
	
	@ManyToOne
	@JoinColumn(name = "id_obra_grupo_pai")
	private ObraGrupo obraGrupoPai;
	
}