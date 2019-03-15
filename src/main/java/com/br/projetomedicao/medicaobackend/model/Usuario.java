package com.br.projetomedicao.medicaobackend.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	private Long id;
	
	@NotNull
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_construtora")
	private Construtora construtora;
	
	private String telefone;
	
	@NotNull
	private String email;
	
	@NotNull
	private String senha;
	
	@NotNull
	private Boolean ativo;
	
	@NotNull
	private Boolean administrador;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_permissao"))
	private List<Permissao> permissoes;

}