package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Obra> listarStatusAtivoPorConstrutora(@RequestParam(required = true) Long idConstrutora) {
		Construtora construtora = new Construtora();
		construtora.setId(idConstrutora);
		return obraRepository.findByAtivoAndConstrutora(true, construtora);
	}

}