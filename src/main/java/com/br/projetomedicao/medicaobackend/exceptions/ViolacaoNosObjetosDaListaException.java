package com.br.projetomedicao.medicaobackend.exceptions;

public class ViolacaoNosObjetosDaListaException extends RuntimeException {

	private static final long serialVersionUID = -1120608266517531275L;
	
	public ViolacaoNosObjetosDaListaException() {
		super("A lista enviada por parâmetro não é válida");
	}

}