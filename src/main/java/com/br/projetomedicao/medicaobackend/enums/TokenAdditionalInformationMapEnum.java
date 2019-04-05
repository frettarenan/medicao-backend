package com.br.projetomedicao.medicaobackend.enums;

public enum TokenAdditionalInformationMapEnum {
	
	ID("id"),
	NOME("nome"),
	ADMINISTRADOR("administrador"),
	ID_CONSTRUTORA("idConstrutora");
	
	private String keyMap;
	
	TokenAdditionalInformationMapEnum(String keyMap) {
        this.keyMap = keyMap;
    }

	public String getKeyMap() {
		return keyMap;
	}
	
}