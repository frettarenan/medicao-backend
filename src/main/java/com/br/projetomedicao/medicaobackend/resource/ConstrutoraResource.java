package com.br.projetomedicao.medicaobackend.resource;

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

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONSTRUTORA') and #oauth2.hasScope('write')")
	public ResponseEntity<Construtora> criar(@Valid @RequestBody Construtora construtora, HttpServletResponse response) {
		Construtora construtoraSalva = construtoraService.salvar(construtora);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, construtoraSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(construtoraSalva);
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONSTRUTORA') and #oauth2.hasScope('read')")
	public ResponseEntity<Construtora> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Construtora> construtora = construtoraRepository.findById(codigo);
		return construtora.isPresent() ? ResponseEntity.ok(construtora.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CONSTRUTORA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		construtoraRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONSTRUTORA') and #oauth2.hasScope('write')")
	public ResponseEntity<Construtora> atualizar(@PathVariable Long codigo, @Valid @RequestBody Construtora construtora) {
		Construtora construtoraSalva = construtoraService.atualizar(codigo, construtora);
		return ResponseEntity.ok(construtoraSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONSTRUTORA') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		construtoraService.atualizarPropriedadeAtivo(codigo, ativo);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONSTRUTORA')")
	public Page<Construtora> pesquisar(@RequestParam(required = false, defaultValue = "%") String razaoSocial, Pageable pageable) {
		return construtoraRepository.findByRazaoSocialContaining(razaoSocial, pageable);
	}

}