package com.br.projetomedicao.medicaobackend.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.dto.LancamentoEstatisticaPessoa;
import com.br.projetomedicao.medicaobackend.mail.Mailer;
import com.br.projetomedicao.medicaobackend.model.LancamentoAlgaworks;
import com.br.projetomedicao.medicaobackend.model.Pessoa;
import com.br.projetomedicao.medicaobackend.model.Usuario;
import com.br.projetomedicao.medicaobackend.repository.LancamentoAlgaworksRepository;
import com.br.projetomedicao.medicaobackend.repository.PessoaRepository;
import com.br.projetomedicao.medicaobackend.repository.UsuarioRepository;
import com.br.projetomedicao.medicaobackend.service.exception.PessoaInexistenteOuInativaException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoAlgaworksService {
	
	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
	
	private static final Logger logger = LoggerFactory.getLogger(LancamentoAlgaworksService.class);

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private LancamentoAlgaworksRepository lancamentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private Mailer mailer;
	
	@Scheduled(cron = "0 0 6 * * *")
	public void avisarSobreLancamentosVencidos() {
		if (logger.isDebugEnabled()) {
			logger.debug("Preparando envio de "
					+ "e-mails de aviso de lançamentos vencidos.");
		}
		
		List<LancamentoAlgaworks> vencidos = lancamentoRepository
				.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		
		if (vencidos.isEmpty()) {
			logger.info("Sem lançamentos vencidos para aviso.");
			
			return;
		}
		
		logger.info("Exitem {} lançamentos vencidos.", vencidos.size());
		
		List<Usuario> destinatarios = usuarioRepository
				.findByPermissoesDescricao(DESTINATARIOS);
		
		if (destinatarios.isEmpty()) {
			logger.warn("Existem lançamentos vencidos, mas o "
					+ "sistema não encontrou destinatários.");
			
			return;
		}
		
		mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);
		
		logger.info("Envio de e-mail de aviso concluído."); 
	}
	
	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception {
		List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/lancamentos-por-pessoa.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
				new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	public LancamentoAlgaworks salvar(LancamentoAlgaworks lancamento) {
		validarPessoa(lancamento);
		
//		if (StringUtils.hasText(lancamento.getAnexo())) {
//			s3.salvar(lancamento.getAnexo());
//		}

		return lancamentoRepository.save(lancamento);
	}

	public LancamentoAlgaworks atualizar(Long id, LancamentoAlgaworks lancamento) {
		LancamentoAlgaworks lancamentoBD = buscarLancamentoExistente(id);
		if (!lancamento.getPessoa().equals(lancamentoBD.getPessoa())) {
			validarPessoa(lancamento);
		}
		
//		if (StringUtils.isEmpty(lancamento.getAnexo())
//				&& StringUtils.hasText(lancamentoBD.getAnexo())) {
//			s3.remover(lancamentoBD.getAnexo());
//		} else if (StringUtils.hasText(lancamento.getAnexo())
//				&& !lancamento.getAnexo().equals(lancamentoBD.getAnexo())) {
//			s3.substituir(lancamentoBD.getAnexo(), lancamento.getAnexo());
//		}

		BeanUtils.copyProperties(lancamento, lancamentoBD, "id");

		return lancamentoRepository.save(lancamentoBD);
	}

	private void validarPessoa(LancamentoAlgaworks lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getId() != null) {
			pessoa = pessoaRepository.getOne(lancamento.getPessoa().getId());
		}

		if (pessoa == null || !pessoa.getAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}

	private LancamentoAlgaworks buscarLancamentoExistente(Long id) {
		Optional<LancamentoAlgaworks> lancamentoBD = lancamentoRepository.findById(id);
		if (!lancamentoBD.isPresent()) {
			throw new IllegalArgumentException();
		}
		return lancamentoBD.get();
	}

}