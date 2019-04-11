package com.br.projetomedicao.medicaobackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.enums.TipoGrupoEnum;
import com.br.projetomedicao.medicaobackend.exceptions.ViolacaoNosObjetosDaListaException;
import com.br.projetomedicao.medicaobackend.model.Grupo;
import com.br.projetomedicao.medicaobackend.model.Medicao;
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

	@Autowired
	private ObraService obraService;

	@Autowired
	private MedicaoService medicaoService;
	
	@Autowired
	private SegurancaService segurancaService;

	public List<Grupo> listarGruposPorObra(Long idObra) {
		Obra obraBD = obraService.buscarObraPeloId(idObra);
		return grupoRepository.findByObraOrderByOrdemAsc(obraBD);
	}

	public List<Grupo> listarGruposPorMedicao(Long idMedicao) {
		Medicao medicaoBD = medicaoService.buscarMedicaoPeloId(idMedicao);
		return grupoRepository.findByObraOrderByOrdemAsc(medicaoBD.getContrato().getObra());
	}

	private int getProximaOrdem(Obra obra) {
		Page<Grupo> ultimaOrdem = grupoRepository.findByObraOrderByOrdemDesc(obra, PageRequest.of(1, 1));
		if (ultimaOrdem.getTotalElements() > 0) {
			Grupo grupoUltimaOrdem = ultimaOrdem.getContent().get(0);
			if (!isGrupoSistema(grupoUltimaOrdem))
				return grupoUltimaOrdem.getOrdem() + 1;
		}
		return 2;
	}

	public List<Grupo> salvarCadastroRapido(List<Grupo> grupos) {
		if (grupos != null && grupos.size() > 0) {
			validaGrupos(grupos);
			Optional<TipoGrupo> tipoGrupoCadastradoPeloUsuario = tipoGrupoRepository.findById(TipoGrupoEnum.CADASTRADO_PELO_USUARIO.getId());
			int ordem = getProximaOrdem(grupos.get(0).getObra());
			for (Grupo grupo : grupos) {
				grupo.setTipoGrupo(tipoGrupoCadastradoPeloUsuario.get());
				grupo.setOrdem(ordem);
				ordem++;
			}
			return grupoRepository.saveAll(grupos);
		}
		return grupos;
	}
	
	private void validaGrupos(List<Grupo> grupos) {
		Long idObra = null;
		for (Grupo grupo : grupos) {
			if (idObra == null) {
				idObra = grupo.getObra().getId();
			} else if (!idObra.equals(grupo.getObra().getId())) {
				throw new ViolacaoNosObjetosDaListaException();
			}
		}
		if (idObra != null) {
			Obra obraBD = obraService.buscarObraPeloId(idObra);
			for (Grupo grupo : grupos) {
				grupo.setObra(obraBD);
			}
		}
	}

	public List<Grupo> salvarOrdenacao(List<Grupo> grupos) {
		validaGrupos(grupos);
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

	private boolean isGrupoSistema(Grupo grupo) {
		return grupo.getTipoGrupo().getId().equals(TipoGrupoEnum.TOTAL.getId()) || grupo.getTipoGrupo().getId().equals(TipoGrupoEnum.SUBTOTAL.getId());
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

	public void removerGruposDeSistemaDaObra(Obra obra) {
		TipoGrupo tipoGrupo;
		List<Grupo> grupos;

		tipoGrupo = new TipoGrupo();
		tipoGrupo.setId(TipoGrupoEnum.TOTAL.getId());
		grupos = grupoRepository.findByObraAndTipoGrupo(obra, tipoGrupo);
		grupoRepository.deleteAll(grupos);

		tipoGrupo = new TipoGrupo();
		tipoGrupo.setId(TipoGrupoEnum.SUBTOTAL.getId());
		grupos = grupoRepository.findByObraAndTipoGrupo(obra, tipoGrupo);
		grupoRepository.deleteAll(grupos);
	}

	public Grupo buscarGrupoPeloId(Long idGrupo) {
		Optional<Grupo> grupoBD = grupoRepository.findById(idGrupo);
		if (!grupoBD.isPresent())
			throw new EmptyResultDataAccessException(1);
		segurancaService.validaPermissaoAcessoPorConstrutora(grupoBD.get().getObra().getConstrutora().getId());
		return grupoBD.get();
	}
	
	public void remover(Long idGrupo) {
		Grupo grupoBD = buscarGrupoPeloId(idGrupo);
		grupoRepository.delete(grupoBD);
	}

	public Grupo atualizar(Long idGrupo, Grupo grupo) {
		Grupo grupoBD = buscarGrupoPeloId(idGrupo);
		grupoBD.setNome(grupo.getNome());
		return grupoRepository.save(grupoBD);
	}

}