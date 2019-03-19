package com.br.projetomedicao.medicaobackend.resource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.projetomedicao.medicaobackend.dto.AlgaworksAnexo;
import com.br.projetomedicao.medicaobackend.dto.AlgaworksLancamentoEstatisticaCategoria;
import com.br.projetomedicao.medicaobackend.dto.AlgaworksLancamentoEstatisticaDia;
import com.br.projetomedicao.medicaobackend.event.RecursoCriadoEvent;
import com.br.projetomedicao.medicaobackend.exceptionhandler.MedicaoBackendExceptionHandler.Erro;
import com.br.projetomedicao.medicaobackend.model.AlgaworksLancamento;
import com.br.projetomedicao.medicaobackend.repository.AlgaworksLancamentoRepository;
import com.br.projetomedicao.medicaobackend.repository.filter.AlgaworksLancamentoFilter;
import com.br.projetomedicao.medicaobackend.repository.projection.AlgaworksResumoLancamento;
import com.br.projetomedicao.medicaobackend.service.AlgaworksLancamentoService;
import com.br.projetomedicao.medicaobackend.service.exception.AlgaworksPessoaInexistenteOuInativaException;

//@RestController
//@RequestMapping("/lancamentos")
public class AlgaworksLancamentoResource {

	@Autowired
	private AlgaworksLancamentoRepository lancamentoRepository;
	
	@Autowired
	private AlgaworksLancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@PostMapping("/anexo")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public AlgaworksAnexo uploadAnexo(@RequestParam MultipartFile anexo) throws IOException {
//		String nome = s3.salvarTemporariamente(anexo);
//		return new Anexo(nome, s3.configurarUrl(nome));
		return null;
	}
	
	@GetMapping("/relatorios/por-pessoa")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioPorPessoa(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio, 
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) throws Exception {
		byte[] relatorio = lancamentoService.relatorioPorPessoa(inicio, fim);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/estatisticas/por-dia")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<AlgaworksLancamentoEstatisticaDia> porDia() {
		return this.lancamentoRepository.porDia(LocalDate.now());
	}
	
	@GetMapping("/estatisticas/por-categoria")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<AlgaworksLancamentoEstatisticaCategoria> porCategoria() {
		return this.lancamentoRepository.porCategoria(LocalDate.now());
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<AlgaworksLancamento> pesquisar(AlgaworksLancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<AlgaworksResumoLancamento> resumir(AlgaworksLancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.resumir(lancamentoFilter, pageable);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<AlgaworksLancamento> buscarPeloId(@PathVariable Long id) {
		Optional<AlgaworksLancamento> lancamento = lancamentoRepository.findById(id);
		return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<AlgaworksLancamento> criar(@Valid @RequestBody AlgaworksLancamento lancamento, HttpServletResponse response) {
		AlgaworksLancamento lancamentoBD = lancamentoService.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoBD.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoBD);
	}
	
	@ExceptionHandler({ AlgaworksPessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(AlgaworksPessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		lancamentoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO')")
	public ResponseEntity<AlgaworksLancamento> atualizar(@PathVariable Long id, @Valid @RequestBody AlgaworksLancamento lancamento) {
		try {
			AlgaworksLancamento lancamentoBD = lancamentoService.atualizar(id, lancamento);
			return ResponseEntity.ok(lancamentoBD);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}