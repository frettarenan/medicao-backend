package com.br.projetomedicao.medicaobackend.enums;

public enum TipoGrupoEnum {
	
	TOTAL(1l, 1),
	SUBTOTAL(2l, 999),
	CADASTRADO_PELO_USUARIO(3l, null);
	
	private long id;
	private Integer ordem;
	
	TipoGrupoEnum(long id, Integer ordem) {
        this.id = id;
        this.ordem = ordem;
    }

	public long getId() {
		return id;
	}

	public Integer getOrdem() {
		return ordem;
	}
	
}