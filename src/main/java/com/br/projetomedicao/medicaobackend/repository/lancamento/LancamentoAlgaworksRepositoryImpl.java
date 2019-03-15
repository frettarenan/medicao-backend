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

import com.br.projetomedicao.medicaobackend.dto.LancamentoEstatisticaCategoria;
import com.br.projetomedicao.medicaobackend.dto.LancamentoEstatisticaDia;
import com.br.projetomedicao.medicaobackend.dto.LancamentoEstatisticaPessoa;
import com.br.projetomedicao.medicaobackend.model.Categoria_;
import com.br.projetomedicao.medicaobackend.model.LancamentoAlgaworks;
import com.br.projetomedicao.medicaobackend.model.LancamentoAlgaworks_;
import com.br.projetomedicao.medicaobackend.model.Pessoa_;
import com.br.projetomedicao.medicaobackend.repository.filter.LancamentoFilter;
import com.br.projetomedicao.medicaobackend.repository.projection.ResumoLancamento;

public class LancamentoAlgaworksRepositoryImpl implements LancamentoAlgaworksRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaPessoa> criteriaQuery = criteriaBuilder.
				createQuery(LancamentoEstatisticaPessoa.class);
		
		Root<LancamentoAlgaworks> root = criteriaQuery.from(LancamentoAlgaworks.class);
		
		criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaPessoa.class, 
				root.get(LancamentoAlgaworks_.tipo),
				root.get(LancamentoAlgaworks_.pessoa),
				criteriaBuilder.sum(root.get(LancamentoAlgaworks_.valor))));
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(LancamentoAlgaworks_.dataVencimento), 
						inicio),
				criteriaBuilder.lessThanOrEqualTo(root.get(LancamentoAlgaworks_.dataVencimento), 
						fim));
		
		criteriaQuery.groupBy(root.get(LancamentoAlgaworks_.tipo), 
				root.get(LancamentoAlgaworks_.pessoa));
		
		TypedQuery<LancamentoEstatisticaPessoa> typedQuery = manager
				.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaDia> criteriaQuery = criteriaBuilder.
				createQuery(LancamentoEstatisticaDia.class);
		
		Root<LancamentoAlgaworks> root = criteriaQuery.from(LancamentoAlgaworks.class);
		
		criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaDia.class, 
				root.get(LancamentoAlgaworks_.tipo),
				root.get(LancamentoAlgaworks_.dataVencimento),
				criteriaBuilder.sum(root.get(LancamentoAlgaworks_.valor))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(LancamentoAlgaworks_.dataVencimento), 
						primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(LancamentoAlgaworks_.dataVencimento), 
						ultimoDia));
		
		criteriaQuery.groupBy(root.get(LancamentoAlgaworks_.tipo), 
				root.get(LancamentoAlgaworks_.dataVencimento));
		
		TypedQuery<LancamentoEstatisticaDia> typedQuery = manager
				.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaCategoria> criteriaQuery = criteriaBuilder.
				createQuery(LancamentoEstatisticaCategoria.class);
		
		Root<LancamentoAlgaworks> root = criteriaQuery.from(LancamentoAlgaworks.class);
		
		criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaCategoria.class, 
				root.get(LancamentoAlgaworks_.categoria),
				criteriaBuilder.sum(root.get(LancamentoAlgaworks_.valor))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(LancamentoAlgaworks_.dataVencimento), 
						primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(LancamentoAlgaworks_.dataVencimento), 
						ultimoDia));
		
		criteriaQuery.groupBy(root.get(LancamentoAlgaworks_.categoria));
		
		TypedQuery<LancamentoEstatisticaCategoria> typedQuery = manager
				.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public Page<LancamentoAlgaworks> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoAlgaworks> criteria = builder.createQuery(LancamentoAlgaworks.class);
		Root<LancamentoAlgaworks> root = criteria.from(LancamentoAlgaworks.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<LancamentoAlgaworks> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	

	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<LancamentoAlgaworks> root = criteria.from(LancamentoAlgaworks.class);
		
		criteria.select(builder.construct(ResumoLancamento.class
				, root.get(LancamentoAlgaworks_.id), root.get(LancamentoAlgaworks_.descricao)
				, root.get(LancamentoAlgaworks_.dataVencimento), root.get(LancamentoAlgaworks_.dataPagamento)
				, root.get(LancamentoAlgaworks_.valor), root.get(LancamentoAlgaworks_.tipo)
				, root.get(LancamentoAlgaworks_.categoria).get(Categoria_.nome)
				, root.get(LancamentoAlgaworks_.pessoa).get(Pessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<LancamentoAlgaworks> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(LancamentoAlgaworks_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(LancamentoAlgaworks_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(LancamentoAlgaworks_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
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
	
	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<LancamentoAlgaworks> root = criteria.from(LancamentoAlgaworks.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
	
}