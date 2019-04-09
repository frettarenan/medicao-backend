package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Grupo;
import com.br.projetomedicao.medicaobackend.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoResource {
	
	@Autowired
	private GrupoService grupoService;
	
	@GetMapping("/obra/{idObra}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_OBRA') and #oauth2.hasScope('write')")
	public List<Grupo> listarGruposPorObra(@PathVariable Long idObra) {
		return grupoService.listarGruposPorObra(idObra);
	}
	
	@GetMapping("/medicao/{idMedicao}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_MEDICAO') and #oauth2.hasScope('write')")
	public List<Grupo> listarGruposPorMedicao(@PathVariable Long idMedicao) {
		return grupoService.listarGruposPorMedicao(idMedicao);
	}
	
	@PostMapping("/cadastro-rapido")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_GRUPO') and #oauth2.hasScope('write')")
	public ResponseEntity<List<Grupo>> salvarCadastroRapido(@Valid @RequestBody List<Grupo> grupos, HttpServletResponse response) {
		List<Grupo> gruposBD = grupoService.salvarCadastroRapido(grupos);
		return ResponseEntity.status(HttpStatus.CREATED).body(gruposBD);
	}
	
	@PostMapping("/ordenar")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_GRUPO') and #oauth2.hasScope('write')")
	public ResponseEntity<List<Grupo>> salvarOrdenacao(@Valid @RequestBody List<Grupo> grupos, HttpServletResponse response) {
		List<Grupo> gruposBD = grupoService.salvarOrdenacao(grupos);
		return ResponseEntity.ok(gruposBD);
	}
	
	@DeleteMapping("/{idGrupo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_GRUPO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long idGrupo) {
		grupoService.remover(idGrupo);
	}

}