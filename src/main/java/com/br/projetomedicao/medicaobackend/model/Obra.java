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
@Table(name = "obra")
public class Obra {
	
	@Id
	private Long id;
	
	@NotNull
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_construtora")
	private Construtora construtora;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario_responsavel")
	private Usuario usuarioResponsavel;
	
	@NotNull
	private Boolean ativo;
	
}