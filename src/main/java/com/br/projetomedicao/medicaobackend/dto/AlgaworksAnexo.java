package com.br.projetomedicao.medicaobackend.dto;

public class AlgaworksAnexo {
	
	private String nome;
	
	private String url;

	public AlgaworksAnexo(String nome, String url) {
		this.nome = nome;
		this.url = url;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getUrl() {
		return url;
	}
}