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

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.service.ContratoService;
import com.br.projetomedicao.medicaobackend.utils.HttpServletResponseUtil;

@RestController
@RequestMapping("/contratos")
public class ContratoResource {
	
	@Autowired
	private ContratoService contratoService;
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Contrato> listarContratoComStatusAtivoPorObra(@RequestParam(required = true) Long idObra) {
		return contratoService.listarContratoComStatusAtivoPorObra(idObra);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONTRATO') and #oauth2.hasScope('read')")
	public Page<Contrato> pesquisar(@RequestParam(required = false) String numero, @RequestParam(required = false) String descricao, @RequestParam(required = false) Long idConstrutora, @RequestParam(required = false) Long idObra, Pageable pageable) {
		return contratoService.pesquisar(numero, descricao, idConstrutora, idObra, pageable);
	}
	
	@PutMapping("/{idContrato}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONTRATO') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long idContrato, @RequestBody Boolean ativo) {
		contratoService.atualizarPropriedadeAtivo(idContrato, ativo);
	}
	
	@DeleteMapping("/{idContrato}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CONTRATO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long idContrato) {
		contratoService.remover(idContrato);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONTRATO') and #oauth2.hasScope('write')")
	public ResponseEntity<Contrato> salvar(@Valid @RequestBody Contrato contrato, HttpServletResponse response) {
		Contrato contratoBD = contratoService.salvar(contrato);
		HttpServletResponseUtil.adicionarHeaderLocation(response, contratoBD.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(contratoBD);
	}
	
	@GetMapping("/{idContrato}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONTRATO') and #oauth2.hasScope('read')")
	public ResponseEntity<Contrato> buscarContratoPeloId(@PathVariable Long idContrato) {
		Contrato contratoBD = contratoService.buscarContratoPeloId(idContrato);
		return ResponseEntity.ok(contratoBD);
	}
	
	@PutMapping("/{idContrato}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONTRATO') and #oauth2.hasScope('write')")
	public ResponseEntity<Contrato> atualizar(@PathVariable Long idContrato, @Valid @RequestBody Contrato contrato) {
		Contrato contratoBD = contratoService.atualizar(idContrato, contrato);
		return ResponseEntity.ok(contratoBD);
	}
	
}