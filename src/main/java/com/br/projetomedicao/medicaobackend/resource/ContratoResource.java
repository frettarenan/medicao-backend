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

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.repository.ContratoRepository;
import com.br.projetomedicao.medicaobackend.service.ContratoService;

@RestController
@RequestMapping("/contratos")
public class ContratoResource {
	
	@Autowired
	private ContratoService contratoService;

	@Autowired
	private ContratoRepository contratoRepository;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public Page<Contrato> pesquisar(@RequestParam(required = false) String numero, @RequestParam(required = false) String descricao, @RequestParam(required = false) Long idConstrutora, @RequestParam(required = false) Long idObra, Pageable pageable) {		
		return contratoRepository.pesquisar(numero, descricao, idConstrutora, idObra, pageable);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONTRATO') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		contratoService.atualizarPropriedadeAtivo(id, ativo);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CONTRATO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		contratoRepository.deleteById(id);
	}
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Contrato> listarStatusAtivoPorContrato(@RequestParam(required = true) Long idObra) {
		Obra obra = new Obra();
		obra.setId(idObra);
		return contratoRepository.findByAtivoAndObra(true, obra);
	}

}