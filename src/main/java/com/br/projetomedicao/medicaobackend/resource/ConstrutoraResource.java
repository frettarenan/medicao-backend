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

import com.br.projetomedicao.medicaobackend.model.Construtora;
import com.br.projetomedicao.medicaobackend.service.ConstrutoraService;
import com.br.projetomedicao.medicaobackend.utils.HttpServletResponseUtil;

@RestController
@RequestMapping("/construtoras")
public class ConstrutoraResource {

	@Autowired
	private ConstrutoraService construtoraService;
	
	@GetMapping("/status/ativo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONSTRUTORA') and #oauth2.hasScope('read')")
	public List<Construtora> listarConstrutorasComStatusAtivo() {
		return construtoraService.listarConstrutorasComStatusAtivo();
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONSTRUTORA') and #oauth2.hasScope('read')")
	public Page<Construtora> pesquisar(@RequestParam(required = false, defaultValue = "%") String razaoSocial, Pageable pageable) {
		return construtoraService.pesquisar(razaoSocial, pageable);
	}
	
	@PutMapping("/{idConstrutora}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONSTRUTORA') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long idConstrutora, @RequestBody Boolean ativo) {
		construtoraService.atualizarPropriedadeAtivo(idConstrutora, ativo);
	}
	
	@DeleteMapping("/{idConstrutora}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CONSTRUTORA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long idConstrutora) {
		construtoraService.remover(idConstrutora);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONSTRUTORA') and #oauth2.hasScope('write')")
	public ResponseEntity<Construtora> salvar(@Valid @RequestBody Construtora construtora, HttpServletResponse response) {
		Construtora construtoraBD = construtoraService.salvar(construtora);
		HttpServletResponseUtil.adicionarHeaderLocation(response, construtoraBD.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(construtoraBD);
	}
	
	@GetMapping("/{idConstrutora}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONSTRUTORA') and #oauth2.hasScope('read')")
	public ResponseEntity<Construtora> buscarConstrutoraPeloId(@PathVariable Long idConstrutora) {
		Construtora construtoraBD = construtoraService.buscarConstrutoraPeloId(idConstrutora);
		return ResponseEntity.ok(construtoraBD);
	}
	
	@PutMapping("/{idConstrutora}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONSTRUTORA') and #oauth2.hasScope('write')")
	public ResponseEntity<Construtora> atualizar(@PathVariable Long idConstrutora, @Valid @RequestBody Construtora construtora) {
		Construtora construtoraBD = construtoraService.atualizar(idConstrutora, construtora);
		return ResponseEntity.ok(construtoraBD);
	}
	
}