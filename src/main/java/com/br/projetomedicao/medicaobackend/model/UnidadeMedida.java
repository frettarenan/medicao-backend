package com.br.projetomedicao.medicaobackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "unidade_medida")
public class UnidadeMedida {
	
	@Id
	private Long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String sigla;
	
}