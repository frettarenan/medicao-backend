package com.br.projetomedicao.medicaobackend.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.enums.TokenAdditionalInformationMapEnum;

@Service
public class AuthorizationTokenService {
	
	@Autowired
	private TokenStore tokenStore;

	public String getAdditionalInformation(TokenAdditionalInformationMapEnum tokenAdditionalInformationMapEnum) {
		OAuth2AuthenticationDetails auth2AuthenticationDetails = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
		Map<String, Object> additionalInfo = tokenStore.readAccessToken(auth2AuthenticationDetails.getTokenValue()).getAdditionalInformation();
		Object object = additionalInfo.get(tokenAdditionalInformationMapEnum.getKeyMap());
		return (object == null) ? null : object.toString();
	}
	
}