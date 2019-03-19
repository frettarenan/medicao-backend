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
import com.br.projetomedicao.medicaobackend.model.AlgaworksPessoa;
import com.br.projetomedicao.medicaobackend.repository.AlgaworksPessoaRepository;
import com.br.projetomedicao.medicaobackend.service.AlgaworksPessoaService;

//@RestController
//@RequestMapping("/pessoas")
public class AlgaworksPessoaResource {

	@Autowired
	private AlgaworksPessoaRepository pessoaRepository;
	
	@Autowired
	private AlgaworksPessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<AlgaworksPessoa> criar(@Valid @RequestBody AlgaworksPessoa pessoa, HttpServletResponse response) {
		AlgaworksPessoa pessoaBD = pessoaService.salvar(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaBD.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaBD);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<AlgaworksPessoa> buscarPeloId(@PathVariable Long id) {
		Optional<AlgaworksPessoa> pessoa = pessoaRepository.findById(id);
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		pessoaRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<AlgaworksPessoa> atualizar(@PathVariable Long id, @Valid @RequestBody AlgaworksPessoa pessoa) {
		AlgaworksPessoa pessoaBD = pessoaService.atualizar(id, pessoa);
		return ResponseEntity.ok(pessoaBD);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(id, ativo);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
	public Page<AlgaworksPessoa> pesquisar(@RequestParam(required = false, defaultValue = "%") String nome, Pageable pageable) {
		return pessoaRepository.findByNomeContaining(nome, pageable);
	}

}