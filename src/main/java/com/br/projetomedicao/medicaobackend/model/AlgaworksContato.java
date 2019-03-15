package com.br.projetomedicao.medicaobackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "contato")
public class AlgaworksContato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nome;

	@Email
	@NotNull
	private String email;

	@NotEmpty
	private String telefone;

	@ManyToOne
	@JoinColumn(name = "id_pessoa")
	private AlgaworksPessoa pessoa;

}
