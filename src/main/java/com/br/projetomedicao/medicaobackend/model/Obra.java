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
@Table(name = "obra")
public class Obra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_construtora")
	private Construtora construtora;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_usuario_responsavel")
	private Usuario usuarioResponsavel;
	
	@NotNull
	private Boolean ativo;
	
}