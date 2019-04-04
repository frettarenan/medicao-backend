package com.br.projetomedicao.medicaobackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.enums.TipoGrupoEnum;
import com.br.projetomedicao.medicaobackend.model.Grupo;
import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.model.TipoGrupo;
import com.br.projetomedicao.medicaobackend.repository.GrupoRepository;
import com.br.projetomedicao.medicaobackend.repository.TipoGrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private TipoGrupoRepository tipoGrupoRepository;

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
	
	private int getProximaOrdem(Obra obra) {
		Page<Grupo> ultimaOrdem = grupoRepository.findByObraOrderByOrdemDesc(obra, PageRequest.of(1, 1));
		if (ultimaOrdem.getSize() > 0) {
			Grupo grupoUltimaOrdem = ultimaOrdem.getContent().get(0);
			if (!isGrupoSistema(grupoUltimaOrdem))
				return grupoUltimaOrdem.getOrdem() +1;
		}
		return 2;
	}

	public List<Grupo> salvarNovosGrupos(List<Grupo> grupos) {
		Optional<TipoGrupo> tipoGrupoCadastradoPeloUsuario = tipoGrupoRepository.findById(TipoGrupoEnum.CADASTRADO_PELO_USUARIO.getId());
		int ordem = getProximaOrdem(grupos.get(0).getObra());
		for (Grupo grupo : grupos) {
			grupo.setTipoGrupo(tipoGrupoCadastradoPeloUsuario.get());
			grupo.setOrdem(ordem);
			ordem++;
		}
		return grupoRepository.saveAll(grupos);		
	}

	public void removerGruposDeSistemaDaObra(Long idObra) {
		TipoGrupo tipoGrupo;
		List<Grupo> grupos;
		
		Obra obra = new Obra();
		obra.setId(idObra);
		
		tipoGrupo = new TipoGrupo();
		tipoGrupo.setId(TipoGrupoEnum.TOTAL.getId());
		grupos = grupoRepository.findByObraAndTipoGrupo(obra, tipoGrupo);
		grupoRepository.deleteAll(grupos);
		
		tipoGrupo = new TipoGrupo();
		tipoGrupo.setId(TipoGrupoEnum.SUBTOTAL.getId());
		grupos = grupoRepository.findByObraAndTipoGrupo(obra, tipoGrupo);
		grupoRepository.deleteAll(grupos);
	}

}