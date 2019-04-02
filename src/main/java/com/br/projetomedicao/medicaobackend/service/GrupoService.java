package com.br.projetomedicao.medicaobackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.enums.TipoGrupoEnum;
import com.br.projetomedicao.medicaobackend.model.Grupo;
import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.model.TipoGrupo;
import com.br.projetomedicao.medicaobackend.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;

	public List<Grupo> salvar(List<Grupo> grupos) {
		return grupoRepository.saveAll(grupos);
	}

	public void salvarGruposDeSistema(Obra obra) {
		salvarGruposDeSistema(obra, TipoGrupoEnum.TOTAL);
		salvarGruposDeSistema(obra, TipoGrupoEnum.SUBTOTAL);
	}

	private void salvarGruposDeSistema(Obra obra, TipoGrupoEnum tipoGrupoEnum) {
		TipoGrupo tipoGrupo = new TipoGrupo();
		tipoGrupo.setId(tipoGrupoEnum.getId());

		Grupo grupo = new Grupo();
		grupo.setNome(tipoGrupoEnum.name());
		grupo.setObra(obra);
		grupo.setTipoGrupo(tipoGrupo);
		grupo.setOrdem(tipoGrupoEnum.getOrdem());
		grupoRepository.save(grupo);
	}

	public List<Grupo> salvarOrdenacao(List<Grupo> grupos) {
		// Passo 1: Altera a ordenação para nulo
		for (Grupo grupo : grupos) {
			if (!isGrupoSistema(grupo))
				grupo.setOrdem(null);
		}
		grupoRepository.saveAll(grupos);
		// Passo 2: Salva a ordenação correta
		int ordem = 2;
		for (Grupo grupo : grupos) {
			if (!isGrupoSistema(grupo)) {
				grupo.setOrdem(ordem);
				ordem++;
			}
		}
		return grupoRepository.saveAll(grupos);
	}

	public boolean isGrupoSistema(Grupo grupo) {
		return grupo.getTipoGrupo().getId().equals(TipoGrupoEnum.TOTAL.getId())
				|| grupo.getTipoGrupo().getId().equals(TipoGrupoEnum.SUBTOTAL.getId());
	}

}