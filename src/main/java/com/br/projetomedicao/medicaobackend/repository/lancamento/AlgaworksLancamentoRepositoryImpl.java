package com.br.projetomedicao.medicaobackend.repository.lancamento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.br.projetomedicao.medicaobackend.dto.AlgaworksLancamentoEstatisticaCategoria;
import com.br.projetomedicao.medicaobackend.dto.AlgaworksLancamentoEstatisticaDia;
import com.br.projetomedicao.medicaobackend.dto.AlgaworksLancamentoEstatisticaPessoa;
import com.br.projetomedicao.medicaobackend.model.AlgaworksCategoria_;
import com.br.projetomedicao.medicaobackend.model.AlgaworksLancamento;
import com.br.projetomedicao.medicaobackend.model.AlgaworksLancamento_;
import com.br.projetomedicao.medicaobackend.model.AlgaworksPessoa_;
import com.br.projetomedicao.medicaobackend.repository.filter.AlgaworksLancamentoFilter;
import com.br.projetomedicao.medicaobackend.repository.projection.AlgaworksResumoLancamento;

public class AlgaworksLancamentoRepositoryImpl implements AlgaworksLancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<AlgaworksLancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<AlgaworksLancamentoEstatisticaPessoa> criteriaQuery = criteriaBuilder.
				createQuery(AlgaworksLancamentoEstatisticaPessoa.class);
		
		Root<AlgaworksLancamento> root = criteriaQuery.from(AlgaworksLancamento.class);
		
		criteriaQuery.select(criteriaBuilder.construct(AlgaworksLancamentoEstatisticaPessoa.class, 
				root.get(AlgaworksLancamento_.tipo),
				root.get(AlgaworksLancamento_.pessoa),
				criteriaBuilder.sum(root.get(AlgaworksLancamento_.valor))));
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(AlgaworksLancamento_.dataVencimento), 
						inicio),
				criteriaBuilder.lessThanOrEqualTo(root.get(AlgaworksLancamento_.dataVencimento), 
						fim));
		
		criteriaQuery.groupBy(root.get(AlgaworksLancamento_.tipo), 
				root.get(AlgaworksLancamento_.pessoa));
		
		TypedQuery<AlgaworksLancamentoEstatisticaPessoa> typedQuery = manager
				.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<AlgaworksLancamentoEstatisticaDia> porDia(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<AlgaworksLancamentoEstatisticaDia> criteriaQuery = criteriaBuilder.
				createQuery(AlgaworksLancamentoEstatisticaDia.class);
		
		Root<AlgaworksLancamento> root = criteriaQuery.from(AlgaworksLancamento.class);
		
		criteriaQuery.select(criteriaBuilder.construct(AlgaworksLancamentoEstatisticaDia.class, 
				root.get(AlgaworksLancamento_.tipo),
				root.get(AlgaworksLancamento_.dataVencimento),
				criteriaBuilder.sum(root.get(AlgaworksLancamento_.valor))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(AlgaworksLancamento_.dataVencimento), 
						primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(AlgaworksLancamento_.dataVencimento), 
						ultimoDia));
		
		criteriaQuery.groupBy(root.get(AlgaworksLancamento_.tipo), 
				root.get(AlgaworksLancamento_.dataVencimento));
		
		TypedQuery<AlgaworksLancamentoEstatisticaDia> typedQuery = manager
				.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<AlgaworksLancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<AlgaworksLancamentoEstatisticaCategoria> criteriaQuery = criteriaBuilder.
				createQuery(AlgaworksLancamentoEstatisticaCategoria.class);
		
		Root<AlgaworksLancamento> root = criteriaQuery.from(AlgaworksLancamento.class);
		
		criteriaQuery.select(criteriaBuilder.construct(AlgaworksLancamentoEstatisticaCategoria.class, 
				root.get(AlgaworksLancamento_.categoria),
				criteriaBuilder.sum(root.get(AlgaworksLancamento_.valor))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(AlgaworksLancamento_.dataVencimento), 
						primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(AlgaworksLancamento_.dataVencimento), 
						ultimoDia));
		
		criteriaQuery.groupBy(root.get(AlgaworksLancamento_.categoria));
		
		TypedQuery<AlgaworksLancamentoEstatisticaCategoria> typedQuery = manager
				.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public Page<AlgaworksLancamento> filtrar(AlgaworksLancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<AlgaworksLancamento> criteria = builder.createQuery(AlgaworksLancamento.class);
		Root<AlgaworksLancamento> root = criteria.from(AlgaworksLancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<AlgaworksLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	

	@Override
	public Page<AlgaworksResumoLancamento> resumir(AlgaworksLancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<AlgaworksResumoLancamento> criteria = builder.createQuery(AlgaworksResumoLancamento.class);
		Root<AlgaworksLancamento> root = criteria.from(AlgaworksLancamento.class);
		
		criteria.select(builder.construct(AlgaworksResumoLancamento.class
				, root.get(AlgaworksLancamento_.id), root.get(AlgaworksLancamento_.descricao)
				, root.get(AlgaworksLancamento_.dataVencimento), root.get(AlgaworksLancamento_.dataPagamento)
				, root.get(AlgaworksLancamento_.valor), root.get(AlgaworksLancamento_.tipo)
				, root.get(AlgaworksLancamento_.categoria).get(AlgaworksCategoria_.nome)
				, root.get(AlgaworksLancamento_.pessoa).get(AlgaworksPessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<AlgaworksResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

	private Predicate[] criarRestricoes(AlgaworksLancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<AlgaworksLancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(AlgaworksLancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(AlgaworksLancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(AlgaworksLancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(AlgaworksLancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<AlgaworksLancamento> root = criteria.from(AlgaworksLancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
	
}