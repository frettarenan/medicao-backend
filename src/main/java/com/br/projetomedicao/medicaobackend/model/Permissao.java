package com.br.projetomedicao.medicaobackend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "permissao")
public class Permissao implements Serializable {

	private static final long serialVersionUID = 8668992560351180623L;

	@Id
	private Long id;
	
	@NotNull
	private String chave;
	
	@NotNull
	private String descricao;

}