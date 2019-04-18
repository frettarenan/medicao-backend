package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Medicao;
import com.br.projetomedicao.medicaobackend.service.MedicaoService;
import com.br.projetomedicao.medicaobackend.utils.HttpServletResponseUtil;

@RestController
@RequestMapping("/medicoes")
public class MedicaoResource {

	@Autowired
	private MedicaoService medicaoService;
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Medicao> listarMedicaoComStatusAtivoPorContrato(@RequestParam(required = true) Long idContrato) {
		return medicaoService.listarMedicaoComStatusAtivoPorContrato(idContrato);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_MEDICAO') and #oauth2.hasScope('write')")
	public ResponseEntity<Medicao> salvar(@Valid @RequestBody Medicao medicao, HttpServletResponse response) {
		Medicao medicaoBD = medicaoService.salvar(medicao);
		HttpServletResponseUtil.adicionarHeaderLocation(response, medicaoBD.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(medicaoBD);
	}
	
	@PostMapping("/{idMedicao}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_MEDICAO') and #oauth2.hasScope('write')")
	public ResponseEntity<Medicao> salvarComo(@PathVariable Long idMedicao, @Valid @RequestBody String nome, HttpServletResponse response) {
		Medicao medicaoBD = medicaoService.salvarComo(idMedicao, nome);
		HttpServletResponseUtil.adicionarHeaderLocation(response, medicaoBD.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(medicaoBD);
	}
	
	@GetMapping("/{idMedicao}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_MEDICAO') and #oauth2.hasScope('read')")
	public ResponseEntity<Medicao> buscarMedicaoPeloId(@PathVariable Long idMedicao) {
		Medicao medicaoBD = medicaoService.buscarMedicaoPeloId(idMedicao);
		return ResponseEntity.ok(medicaoBD);
	}
	
	@PutMapping("/{idMedicao}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_MEDICAO') and #oauth2.hasScope('write')")
	public ResponseEntity<Medicao> renomear(@PathVariable Long idMedicao, @Valid @RequestBody String nome) {
		Medicao medicaoBD = medicaoService.renomear(idMedicao, nome);
		return ResponseEntity.ok(medicaoBD);
	}
	
	@GetMapping("/{idMedicao}/relatorio")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_MEDICAO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioMedicao(@PathVariable Long idMedicao) throws Exception {
		byte[] relatorio = medicaoService.relatorioMedicao(idMedicao);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
	}

}