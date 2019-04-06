package com.br.projetomedicao.medicaobackend.config.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.br.projetomedicao.medicaobackend.enums.TokenAdditionalInformationMapEnum;
import com.br.projetomedicao.medicaobackend.model.Usuario;
import com.br.projetomedicao.medicaobackend.security.UsuarioSistema;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
		Usuario usuario = usuarioSistema.getUsuario();
		
		Map<String, Object> addInfo = new HashMap<>();
		addInfo.put(TokenAdditionalInformationMapEnum.ID.getKeyMap(), usuario.getId());
		addInfo.put(TokenAdditionalInformationMapEnum.NOME.getKeyMap(), usuario.getNome());
		addInfo.put(TokenAdditionalInformationMapEnum.ADMINISTRADOR.getKeyMap(), usuario.getAdministrador());
		addInfo.put(TokenAdditionalInformationMapEnum.ID_CONSTRUTORA.getKeyMap(), usuario.getConstrutora() != null ? usuario.getConstrutora().getId() : null);
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
		return accessToken;
	}

}