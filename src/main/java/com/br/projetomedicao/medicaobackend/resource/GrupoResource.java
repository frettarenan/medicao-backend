package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Grupo;
import com.br.projetomedicao.medicaobackend.model.Medicao;
import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.repository.GrupoRepository;
import com.br.projetomedicao.medicaobackend.repository.MedicaoRepository;
import com.br.projetomedicao.medicaobackend.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoResource {
	
	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private MedicaoRepository medicaoRepository;
	
	@GetMapping("/obra/{idObra}")
	@PreAuthorize("isAuthenticated()")
	public List<Grupo> listarTodosPorObra(@PathVariable Long idObra, Pageable pageable) {
		Obra obra = new Obra();
		obra.setId(idObra);
		List<Grupo> grupos = grupoRepository.findByObraOrderByOrdemAsc(obra);
		return grupos;
	}
	
	@GetMapping("/medicao/{idMedicao}")
	@PreAuthorize("isAuthenticated()")
	public List<Grupo> listarGruposPorMedicao(@PathVariable Long idMedicao) {
		Medicao contratoMedicao = medicaoRepository.findById(idMedicao).get();
		List<Grupo> grupos = grupoRepository.findByObraOrderByOrdemAsc(contratoMedicao.getContrato().getObra());
		return grupos;
	}
	
	@PostMapping("/cadastro-rapido")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_GRUPO') and #oauth2.hasScope('write')")
	public ResponseEntity<List<Grupo>> salvarNovosGrupos(@Valid @RequestBody List<Grupo> grupos, HttpServletResponse response) {
		List<Grupo> gruposBD = grupoService.salvarNovosGrupos(grupos);
		return ResponseEntity.status(HttpStatus.CREATED).body(gruposBD);
	}
	
	@PostMapping("/ordenar")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_GRUPO') and #oauth2.hasScope('write')")
	public ResponseEntity<List<Grupo>> salvarOrdenacao(@Valid @RequestBody List<Grupo> grupos, HttpServletResponse response) {
		List<Grupo> gruposBD = grupoService.salvarOrdenacao(grupos);
		return ResponseEntity.status(HttpStatus.CREATED).body(gruposBD);
	}

}