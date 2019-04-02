package com.br.projetomedicao.medicaobackend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "tipo_grupo")
public class TipoGrupo implements Serializable {
	
	private static final long serialVersionUID = 2983569356729552259L;

	@Id
	private Long id;
	
	@NotNull
	private String nome;
	
}