package com.br.projetomedicao.medicaobackend.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

//@EntityListeners(LancamentoAnexoListener.class)
@Data
@Entity
@Table(name = "lancamento")
public class AlgaworksLancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String descricao;

	@NotNull
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@NotNull
	private BigDecimal valor;

	private String observacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AlgaworksTipoLancamento tipo;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private AlgaworksCategoria categoria;

	@JsonIgnoreProperties("contatos")
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_pessoa")
	private AlgaworksPessoa pessoa;

	private String anexo;

	@Transient
	private String urlAnexo;

	@JsonIgnore
	public boolean isReceita() {
		return AlgaworksTipoLancamento.RECEITA.equals(tipo);
	}

}
