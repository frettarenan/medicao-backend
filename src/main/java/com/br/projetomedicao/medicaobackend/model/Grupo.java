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
@Table(name = "grupo")
public class Grupo {
	
	@Id
	private Long id;
	
	@NotNull
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_grupo")
	private TipoGrupo tipoGrupo;
	
	@ManyToOne
	@JoinColumn(name = "id_obra")
	private Obra obra;
	
}