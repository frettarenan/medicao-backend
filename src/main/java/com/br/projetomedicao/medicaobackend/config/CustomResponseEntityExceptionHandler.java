package com.br.projetomedicao.medicaobackend.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br.projetomedicao.medicaobackend.config.property.MessagesProperty;
import com.br.projetomedicao.medicaobackend.enums.MessagesPropertyEnum;
import com.br.projetomedicao.medicaobackend.exceptions.ViolacaoSegurancaException;
import com.mysql.jdbc.MysqlErrorNumbers;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessagesProperty messagesProperty;

	@ExceptionHandler({ ViolacaoSegurancaException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(RuntimeException ex, WebRequest request) {
		String mensagemUsuario = ex.getMessage();
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagemUsuario = messagesProperty.getMessage(MessagesPropertyEnum.ERRO__COMUNICACAO);
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String mensagemUsuario = messagesProperty.getMessage(MessagesPropertyEnum.ERRO__REGISTRO_NAO_EXISTE);
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		Erro erro = getErroComTratamentoMySQL(ex);
		List<Erro> erros = Arrays.asList(erro);
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private Erro getErroComTratamentoMySQL(RuntimeException ex) {
		ConstraintViolationException constraintViolationException = getConstraintViolationException(ex);
		MySQLIntegrityConstraintViolationException mySQLIntegrityConstraintViolationException = getMySQLIntegrityConstraintViolationException(ex);

		if (mySQLIntegrityConstraintViolationException != null) {

			String constraintName = constraintViolationException.getConstraintName();

			if (mySQLIntegrityConstraintViolationException.getErrorCode() == MysqlErrorNumbers.ER_ROW_IS_REFERENCED_2) {
				// SQLSTATE: 23000 Message: Cannot delete or update a parent row: a foreign key
				// constraint fails (%s)
				return new Erro(messagesProperty.getMessage(MessagesPropertyEnum.ERRO__EXISTEM_REGISTROS_DEPENDENTES), mySQLIntegrityConstraintViolationException.getMessage());

			} else if (mySQLIntegrityConstraintViolationException.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
				// SQLSTATE: 23000 Message: Duplicate entry '%s' for key %d
				MessagesPropertyEnum messagesPropertyEnum = MessagesPropertyEnum.ERRO__DUPLICIDADE_BANCO_DADOS;

				if (constraintName != null) {
					if (constraintName.equalsIgnoreCase("unique_cnpj_construtora")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_CNPJ_CONSTRUTORA; // Tabela de construtora

					} else if (constraintName.equalsIgnoreCase("unique_email_usuario")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_EMAIL_USUARIO; // Tabela de usuario

					} else if (constraintName.equalsIgnoreCase("unique_nome_obra_por_construtora")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_NOME_OBRA_POR_CONSTRUTORA; // Tabela de obra

					} else if (constraintName.equalsIgnoreCase("unique_nome_tipo_grupo")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_NOME_TIPO_GRUPO; // Tabela de tipo_grupo

					} else if (constraintName.equalsIgnoreCase("unique_nome_grupo_por_obra")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_NOME_GRUPO_POR_OBRA; // Tabela de grupo

					} else if (constraintName.equalsIgnoreCase("unique_ordem_grupo_por_obra")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_ORDEM_GRUPO_POR_OBRA; // Tabela de grupo

					} else if (constraintName.equalsIgnoreCase("unique_descricao_contrato_por_obra")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_DESCRICAO_CONTRATO_POR_OBRA; // Tabela de contrato

					} else if (constraintName.equalsIgnoreCase("unique_numero_contrato_por_obra")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_NUMERO_CONTRATO_POR_OBRA; // Tabela de contrato

					} else if (constraintName.equalsIgnoreCase("unique_sigla_unidade_medida")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_SIGLA_UNIDADE_MEDIDA; // Tabela de unidade_medida

					} else if (constraintName.equalsIgnoreCase("unique_nome_unidade_medida")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_NOME_UNIDADE_MEDIDA; // Tabela de unidade_medida

					} else if (constraintName.equalsIgnoreCase("unique_nome_servico_por_contrato")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_NOME_SERVICO_POR_CONTRATO; // Tabela de servico

					} else if (constraintName.equalsIgnoreCase("unique_ordem_servico_por_contrato")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_ORDEM_SERVICO_POR_CONTRATO; // Tabela de servico

					} else if (constraintName.equalsIgnoreCase("unique_nome_medicao_por_contrato")) {
						messagesPropertyEnum = MessagesPropertyEnum.ERRO__UNIQUE_NOME_MEDICAO_POR_CONTRATO; // Tabela de medicao
					}
				}
				return new Erro(messagesProperty.getMessage(messagesPropertyEnum), mySQLIntegrityConstraintViolationException.getMessage());
			}
		}
		return new Erro(messagesProperty.getMessage(MessagesPropertyEnum.ERRO__INTEGRIDADE_BANCO_DADOS), ExceptionUtils.getRootCauseMessage(ex));
	}

	private ConstraintViolationException getConstraintViolationException(RuntimeException ex) {
		if (ex.getCause() instanceof ConstraintViolationException)
			return (ConstraintViolationException) ex.getCause();
		return null;
	}

	private MySQLIntegrityConstraintViolationException getMySQLIntegrityConstraintViolationException(RuntimeException ex) {
		Throwable rootEx = ExceptionUtils.getRootCause(ex);
		if (rootEx instanceof MySQLIntegrityConstraintViolationException)
			return (MySQLIntegrityConstraintViolationException) rootEx;
		return null;
	}

	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messagesProperty.getMessage(fieldError);
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}
		return erros;
	}

	public static class Erro {

		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}

	}

}