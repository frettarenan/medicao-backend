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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Servico;
import com.br.projetomedicao.medicaobackend.service.ServicoService;

@RestController
@RequestMapping("/servicos")
public class ServicoResource {

	@Autowired
	private ServicoService servicoService;
	
	@GetMapping("/contrato/{idContrato}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONTRATO') and #oauth2.hasScope('write')")
	public List<Servico> listarServicosPorContrato(@PathVariable Long idContrato) {
		return servicoService.listarServicosPorContrato(idContrato);
	}
	
	@GetMapping("/medicao/{idMedicao}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_MEDICAO') and #oauth2.hasScope('write')")
	public List<Servico> listarServicosPorMedicao(@PathVariable Long idMedicao) {
		return servicoService.listarServicosPorMedicao(idMedicao);
	}
	
	@PostMapping("/cadastro-rapido")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_SERVICO') and #oauth2.hasScope('write')")
	public ResponseEntity<List<Servico>> salvarCadastroRapido(@Valid @RequestBody List<Servico> servicos, HttpServletResponse response) {
		List<Servico> servicosBD = servicoService.salvarCadastroRapido(servicos);
		return ResponseEntity.status(HttpStatus.CREATED).body(servicosBD);
	}
	
	@PostMapping("/ordenar")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_SERVICO') and #oauth2.hasScope('write')")
	public ResponseEntity<List<Servico>> salvarOrdenacao(@Valid @RequestBody List<Servico> servicos, HttpServletResponse response) {
		List<Servico> servicosBD = servicoService.salvarOrdenacao(servicos);
		return ResponseEntity.ok(servicosBD);
	}
	
	@DeleteMapping("/{idServico}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_SERVICO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long idServico) {
		servicoService.remover(idServico);
	}
	
	@PutMapping("/{idServico}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_SERVICO') and #oauth2.hasScope('write')")
	public ResponseEntity<Servico> atualizar(@PathVariable Long idServico, @Valid @RequestBody Servico servico) {
		Servico servicoBD = servicoService.atualizar(idServico, servico);
		return ResponseEntity.ok(servicoBD);
	}

}