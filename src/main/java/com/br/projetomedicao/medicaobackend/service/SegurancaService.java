package com.br.projetomedicao.medicaobackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.enums.TokenAdditionalInformationMapEnum;
import com.br.projetomedicao.medicaobackend.exceptions.ViolacaoSegurancaException;
import com.br.projetomedicao.medicaobackend.model.Construtora;
import com.br.projetomedicao.medicaobackend.model.Usuario;

@Service
public class SegurancaService {
	
	@Autowired
    private AuthorizationTokenService authorizationTokenService;
	
	public void validaPermissaoAcessoSomenteAdministrador() {
		String administrador = authorizationTokenService.getAdditionalInformation(TokenAdditionalInformationMapEnum.ADMINISTRADOR);
		if (!administrador.equalsIgnoreCase("true")) {
			throw new ViolacaoSegurancaException();
		}
	}
	
	public void validaPermissaoAcessoPorConstrutora(Long idConstrutoraValidar) {
		String idConstrutoraUsuarioLogado = authorizationTokenService.getAdditionalInformation(TokenAdditionalInformationMapEnum.ID_CONSTRUTORA);
		String administrador = authorizationTokenService.getAdditionalInformation(TokenAdditionalInformationMapEnum.ADMINISTRADOR);
		if (!administrador.equalsIgnoreCase("true") && idConstrutoraUsuarioLogado != null) {
			if (idConstrutoraValidar == null || !idConstrutoraUsuarioLogado.equals(idConstrutoraValidar.toString())) {
				throw new ViolacaoSegurancaException();
			}
		}
	}
	
	public void validaPermissaoAcessoDaConstrutoraDoUsuario(Construtora construtora, Usuario usuario) {
		if (!usuario.getConstrutora().getId().equals(construtora.getId())) {
			throw new ViolacaoSegurancaException();
		}
	}
	
}