package com.br.projetomedicao.medicaobackend.enums;

public enum TipoGrupoEnum {
	
	TOTAL(1l),
	SUB_TOTAL(2l),
	CADASTRADO_PELO_USUARIO(3l);
	
	private long id;
	
	TipoGrupoEnum(long id) {
        this.id = id;
    }

	public long getId() {
		return id;
	}
	
}