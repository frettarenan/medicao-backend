package com.br.projetomedicao.medicaobackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "construtora")
public class Construtora {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotNull
	private String razaoSocial;
	
	@NotNull
	private String cnpj;

	@NotNull
	private Boolean ativo;
	
}