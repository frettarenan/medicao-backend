package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

import com.br.projetomedicao.medicaobackend.event.RecursoCriadoEvent;
import com.br.projetomedicao.medicaobackend.model.Construtora;
import com.br.projetomedicao.medicaobackend.repository.ConstrutoraRepository;
import com.br.projetomedicao.medicaobackend.service.ConstrutoraService;

@RestController
@RequestMapping("/construtoras")
public class ConstrutoraResource {

	@Autowired
	private ConstrutoraRepository construtoraRepository;
	
	@Autowired
	private ConstrutoraService construtoraService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public Page<Construtora> pesquisar(@RequestParam(required = false, defaultValue = "%") String razaoSocial, Pageable pageable) {
		return construtoraRepository.findByRazaoSocialContaining(razaoSocial, pageable);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONSTRUTORA') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		construtoraService.atualizarPropriedadeAtivo(id, ativo);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CONSTRUTORA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		construtoraRepository.deleteById(id);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONSTRUTORA') and #oauth2.hasScope('write')")
	public ResponseEntity<Construtora> criar(@Valid @RequestBody Construtora construtora, HttpServletResponse response) {
		Construtora construtoraBD = construtoraService.salvar(construtora);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, construtoraBD.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(construtoraBD);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONSTRUTORA') and #oauth2.hasScope('read')")
	public ResponseEntity<Construtora> buscarPeloId(@PathVariable Long id) {
		Optional<Construtora> construtora = construtoraRepository.findById(id);
		return construtora.isPresent() ? ResponseEntity.ok(construtora.get()) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONSTRUTORA') and #oauth2.hasScope('write')")
	public ResponseEntity<Construtora> atualizar(@PathVariable Long id, @Valid @RequestBody Construtora construtora) {
		Construtora construtoraBD = construtoraService.atualizar(id, construtora);
		return ResponseEntity.ok(construtoraBD);
	}
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Construtora> listarStatusAtivo() {
		return construtoraRepository.findByAtivo(true);
	}

}