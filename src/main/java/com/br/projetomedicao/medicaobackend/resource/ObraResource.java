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
import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.repository.ObraRepository;
import com.br.projetomedicao.medicaobackend.service.ObraService;

@RestController
@RequestMapping("/obras")
public class ObraResource {
	
	@Autowired
	private ObraService obraService;

	@Autowired
	private ObraRepository obraRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public Page<Obra> pesquisar(@RequestParam(required = false) String nome, @RequestParam(required = false) Long idConstrutora, Pageable pageable) {		
		return obraRepository.pesquisar(nome, idConstrutora, pageable);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_OBRA') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		obraService.atualizarPropriedadeAtivo(id, ativo);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_OBRA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		obraRepository.deleteById(id);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_OBRA') and #oauth2.hasScope('write')")
	public ResponseEntity<Obra> criar(@Valid @RequestBody Obra obra, HttpServletResponse response) {
		Obra obraBD = obraService.salvar(obra);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, obraBD.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(obraBD);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_OBRA') and #oauth2.hasScope('read')")
	public ResponseEntity<Obra> buscarPeloId(@PathVariable Long id) {
		Optional<Obra> obra = obraRepository.findById(id);
		return obra.isPresent() ? ResponseEntity.ok(obra.get()) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_OBRA') and #oauth2.hasScope('write')")
	public ResponseEntity<Obra> atualizar(@PathVariable Long id, @Valid @RequestBody Obra obra) {
		Obra obraBD = obraService.atualizar(id, obra);
		return ResponseEntity.ok(obraBD);
	}
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Obra> listarStatusAtivoPorConstrutora(@RequestParam(required = true) Long idConstrutora) {
		Construtora construtora = new Construtora();
		construtora.setId(idConstrutora);
		return obraRepository.findByAtivoAndConstrutora(true, construtora);
	}

}