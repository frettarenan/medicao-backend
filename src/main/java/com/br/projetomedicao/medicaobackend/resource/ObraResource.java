package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.service.ObraService;
import com.br.projetomedicao.medicaobackend.utils.HttpServletResponseUtil;

@RestController
@RequestMapping("/obras")
public class ObraResource {
	
	@Autowired
	private ObraService obraService;
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Obra> listarObrasComStatusAtivoPorConstrutora(@RequestParam(required = true) Long idConstrutora) {
		return obraService.listarObrasComStatusAtivoPorConstrutora(idConstrutora);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_OBRA') and #oauth2.hasScope('read')")
	public Page<Obra> pesquisar(@RequestParam(required = false) String nome, @RequestParam(required = false) Long idConstrutora, Pageable pageable) {		
		return obraService.pesquisar(nome, idConstrutora, pageable);
	}
	
	@PutMapping("/{idObra}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_OBRA') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long idObra, @RequestBody Boolean ativo) {
		obraService.atualizarPropriedadeAtivo(idObra, ativo);
	}
	
	@DeleteMapping("/{idObra}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_OBRA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long idObra) {
		obraService.remover(idObra);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_OBRA') and #oauth2.hasScope('write')")
	public ResponseEntity<Obra> salvar(@Valid @RequestBody Obra obra, HttpServletResponse response) {
		Obra obraBD = obraService.salvar(obra);
		HttpServletResponseUtil.adicionarHeaderLocation(response, obraBD.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(obraBD);
	}
	
	@GetMapping("/{idObra}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_OBRA') and #oauth2.hasScope('read')")
	public ResponseEntity<Obra> buscarPeloId(@PathVariable Long idObra) {
		Obra obraBD = obraService.buscarObraPeloId(idObra);
		return ResponseEntity.ok(obraBD);
	}
	
	@PutMapping("/{idObra}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_OBRA') and #oauth2.hasScope('write')")
	public ResponseEntity<Obra> atualizar(@PathVariable Long idObra, @Valid @RequestBody Obra obra) {
		Obra obraBD = obraService.atualizar(idObra, obra);
		return ResponseEntity.ok(obraBD);
	}

}