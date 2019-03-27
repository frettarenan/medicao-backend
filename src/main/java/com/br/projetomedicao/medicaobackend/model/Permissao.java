package com.br.projetomedicao.medicaobackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "permissao")
public class Permissao {

	@Id
	private Long id;
	
	@NotNull
	private String chave;
	
	@NotNull
	private String descricao;

}