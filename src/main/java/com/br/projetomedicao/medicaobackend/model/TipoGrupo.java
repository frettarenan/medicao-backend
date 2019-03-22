package com.br.projetomedicao.medicaobackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "tipo_grupo")
public class TipoGrupo {
	
	@Id
	private Long id;
	
	@NotNull
	private String nome;
	
}