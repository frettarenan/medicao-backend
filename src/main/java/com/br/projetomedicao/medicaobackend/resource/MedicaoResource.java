package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Medicao;
import com.br.projetomedicao.medicaobackend.repository.MedicaoRepository;
import com.br.projetomedicao.medicaobackend.service.MedicaoService;

@RestController
@RequestMapping("/medicoes")
public class MedicaoResource {

	@Autowired
	private MedicaoRepository medicaoRepository;
	
	@Autowired
	private MedicaoService medicaoService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_MEDICAO') and #oauth2.hasScope('read')")
	public ResponseEntity<Medicao> buscarPeloId(@PathVariable Long id) {
		Optional<Medicao> medicao = medicaoRepository.findById(id);
		return medicao.isPresent() ? ResponseEntity.ok(medicao.get()) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_MEDICAO') and #oauth2.hasScope('write')")
	public ResponseEntity<Medicao> atualizar(@PathVariable Long id, @Valid @RequestBody Medicao medicao) {
		Medicao medicaoBD = medicaoService.atualizar(id, medicao);
		return ResponseEntity.ok(medicaoBD);
	}
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Medicao> listarStatusAtivoPorContrato(@RequestParam(required = true) Long idContrato) {
		Contrato contrato = new Contrato();
		contrato.setId(idContrato);
		return medicaoRepository.findByContrato(contrato);
	}

}