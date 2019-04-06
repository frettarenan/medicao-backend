package com.br.projetomedicao.medicaobackend.exceptions;

public class ViolacaoSegurancaException extends RuntimeException {

	private static final long serialVersionUID = -1120608266517531275L;
	
	public ViolacaoSegurancaException() {
		super("Você não tem permissão para executar esta ação");
	}

}