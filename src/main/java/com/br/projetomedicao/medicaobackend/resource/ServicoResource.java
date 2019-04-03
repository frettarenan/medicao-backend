package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Medicao;
import com.br.projetomedicao.medicaobackend.model.Servico;
import com.br.projetomedicao.medicaobackend.repository.MedicaoRepository;
import com.br.projetomedicao.medicaobackend.repository.ServicoRepository;
import com.br.projetomedicao.medicaobackend.service.ServicoService;

@RestController
@RequestMapping("/servicos")
public class ServicoResource {

	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private MedicaoRepository medicaoRepository;
	
	@Autowired
	private ServicoService servicoService;
	
	@GetMapping("/contrato/{idContrato}")
	@PreAuthorize("isAuthenticated()")
	public List<Servico> listarTodosPorContrato(@PathVariable Long idContrato) {
		Contrato contrato = new Contrato();
		contrato.setId(idContrato);
		return servicoRepository.findByContratoOrderByOrdemAsc(contrato);
	}
	
	@GetMapping("/medicao/{idMedicao}")
	@PreAuthorize("isAuthenticated()")
	public List<Servico> listarServicosPorMedicao(@PathVariable Long idMedicao) {
		Medicao medicao = medicaoRepository.findById(idMedicao).get();
		return servicoRepository.findByContratoOrderByOrdemAsc(medicao.getContrato());
	}
	
	@PostMapping("/cadastro-rapido")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_SERVICO') and #oauth2.hasScope('write')")
	public ResponseEntity<List<Servico>> criarServicos(@Valid @RequestBody List<Servico> servicos, HttpServletResponse response) {
		List<Servico> servicosBD = servicoService.salvarNovosServicos(servicos);
		return ResponseEntity.status(HttpStatus.CREATED).body(servicosBD);
	}
	
	@PostMapping("/ordenar")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_SERVICO') and #oauth2.hasScope('write')")
	public ResponseEntity<List<Servico>> salvarOrdenacao(@Valid @RequestBody List<Servico> servicos, HttpServletResponse response) {
		List<Servico> servicosBD = servicoService.salvarOrdenacao(servicos);
		return ResponseEntity.status(HttpStatus.CREATED).body(servicosBD);
	}

}