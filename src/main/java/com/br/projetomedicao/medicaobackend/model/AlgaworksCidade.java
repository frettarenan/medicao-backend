package com.br.projetomedicao.medicaobackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "cidade")
public class AlgaworksCidade {

	@Id
	private Long id;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_estado")
	private AlgaworksEstado estado;

}
