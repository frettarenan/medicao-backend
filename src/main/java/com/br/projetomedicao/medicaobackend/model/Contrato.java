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
@Table(name = "contrato")
public class Contrato {
	
	@Id
	private Long id;
	
	private String numero;
	
	@NotNull
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_obra")
	private Obra obra;
	
}