package com.br.projetomedicao.medicaobackend.model;

import java.io.Serializable;

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
public class Construtora implements Serializable {

	private static final long serialVersionUID = 5397058471419958150L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String razaoSocial;
	
	private String cnpj;

	@NotNull
	private Boolean ativo;
	
}