package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Grupo;
import com.br.projetomedicao.medicaobackend.model.Medicao;
import com.br.projetomedicao.medicaobackend.repository.GrupoRepository;
import com.br.projetomedicao.medicaobackend.repository.MedicaoRepository;

@RestController
@RequestMapping("/grupos")
public class GrupoResource {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private MedicaoRepository medicaoRepository;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Grupo> listarGruposPorMedicao(@RequestParam(required = true) Long idMedicao) {
		Medicao contratoMedicao = medicaoRepository.findById(idMedicao).get();
		List<Grupo> grupos = grupoRepository.findByObra(contratoMedicao.getContrato().getObra());
		Grupo grupoTipoGrupo1 = null; // TOTAL: primeira coluna
		Grupo grupoTipoGrupo2 = null; // SUBTOTAL: última coluna
		for (Grupo grupo : grupos) {
			if (grupo.getTipoGrupo().getId().equals(1L)) {
				grupoTipoGrupo1 = grupo;
			} else if (grupo.getTipoGrupo().getId().equals(2L)) {
				grupoTipoGrupo2 = grupo;
			}
			if (grupoTipoGrupo1 != null && grupoTipoGrupo2 != null) {
				break;
			}
		}
		grupos.remove(grupoTipoGrupo1);
		grupos.remove(grupoTipoGrupo2);
		grupos.add(0, grupoTipoGrupo1);
		grupos.add(grupos.size(), grupoTipoGrupo2);
		return grupos;
	}

}