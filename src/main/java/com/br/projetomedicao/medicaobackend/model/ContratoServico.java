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
@Table(name = "contrato_servico")
public class ContratoServico {
	
	@Id
	private Long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_contrato")
	private Contrato contrato;
	
}