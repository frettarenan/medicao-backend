package com.br.projetomedicao.medicaobackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_tipo_grupo")
	private TipoGrupo tipoGrupo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_obra")
	private Obra obra;
	
}