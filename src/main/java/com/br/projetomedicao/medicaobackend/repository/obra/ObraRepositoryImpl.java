package com.br.projetomedicao.medicaobackend.repository.obra;

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

import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.model.Obra_;

public class ObraRepositoryImpl implements ObraRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	private Long total(String nome, Long idConstrutora) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Obra> root = criteria.from(Obra.class);

		Predicate[] predicates = criarRestricoes(nome, idConstrutora, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private Predicate[] criarRestricoes(String nome, Long idConstrutora, CriteriaBuilder builder,
			Root<Obra> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(nome)) {
			predicates.add(builder.like(builder.lower(root.get(Obra_.nome)), "%" + nome + "%"));
		}

		if (idConstrutora != null) {
			predicates.add(builder.equal(root.get(Obra_.construtora), idConstrutora));
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

	@Override
	public Page<Obra> pesquisar(String nome, Long idConstrutora, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Obra> criteria = builder.createQuery(Obra.class);
		Root<Obra> root = criteria.from(Obra.class);

		Predicate[] predicates = criarRestricoes(nome, idConstrutora, builder, root);
		criteria.where(predicates);

		TypedQuery<Obra> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(nome, idConstrutora));
	}

}