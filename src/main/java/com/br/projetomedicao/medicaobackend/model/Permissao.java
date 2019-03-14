package com.br.projetomedicao.medicaobackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "permissao")
public class Permissao {

	@Id
	private Long id;
	private String chave;
	private String descricao;

}