package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Lancamento;
import com.br.projetomedicao.medicaobackend.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_MEDICAO') and #oauth2.hasScope('write')")
	public List<Lancamento> listarLancamentosPorMedicao(@RequestParam(required = true) Long idMedicao) {
		return lancamentoService.listarLancamentosPorMedicao(idMedicao);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_MEDICAO') and #oauth2.hasScope('write')")
	public ResponseEntity<List<Lancamento>> salvarTodos(@Valid @RequestBody List<Lancamento> lancamentos, HttpServletResponse response) {
		List<Lancamento> lancamentosBD = lancamentoService.salvarTodos(lancamentos);
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentosBD);
	}

}