package com.br.projetomedicao.medicaobackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.enums.TipoGrupoEnum;
import com.br.projetomedicao.medicaobackend.model.Grupo;
import com.br.projetomedicao.medicaobackend.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;

	public List<Grupo> salvar(List<Grupo> grupos) {
		for (Grupo grupo : grupos) {
			grupoRepository.save(grupo);
		}
		return grupos;
	}

	public void organizaGruposDeSistema(List<Grupo> grupos) {
		Grupo grupoTipoGrupoTotal = null;
		Grupo grupoTipoGrupoSubTotal = null;
		for (Grupo grupo : grupos) {
			if (grupo.getTipoGrupo().getId().equals(TipoGrupoEnum.TOTAL.getId())) {
				grupoTipoGrupoTotal = grupo;
			} else if (grupo.getTipoGrupo().getId().equals(TipoGrupoEnum.SUB_TOTAL.getId())) {
				grupoTipoGrupoSubTotal = grupo;
			}
			if (grupoTipoGrupoTotal != null && grupoTipoGrupoSubTotal != null) {
				break;
			}
		}
		grupos.remove(grupoTipoGrupoTotal);
		grupos.remove(grupoTipoGrupoSubTotal);
		grupos.add(0, grupoTipoGrupoTotal);
		grupos.add(grupos.size(), grupoTipoGrupoSubTotal);		
	}

}