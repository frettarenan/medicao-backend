package com.br.projetomedicao.medicaobackend.repository.usuario;

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

import com.br.projetomedicao.medicaobackend.model.Usuario;
import com.br.projetomedicao.medicaobackend.model.Usuario_;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	private Long total(String nome, String email, Long idConstrutora) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		Predicate[] predicates = criarRestricoes(nome, email, idConstrutora, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private Predicate[] criarRestricoes(String nome, String email, Long idConstrutora, CriteriaBuilder builder,
			Root<Usuario> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(nome)) {
			predicates.add(builder.like(builder.lower(root.get(Usuario_.nome)), "%" + nome + "%"));
		}

		if (!StringUtils.isEmpty(email)) {
			predicates.add(builder.like(builder.lower(root.get(Usuario_.email)), "%" + email + "%"));
		}

		if (idConstrutora != null) {
			predicates.add(builder.equal(root.get(Usuario_.construtora), idConstrutora));
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
	public Page<Usuario> pesquisar(String nome, String email, Long idConstrutora, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		Predicate[] predicates = criarRestricoes(nome, email, idConstrutora, builder, root);
		criteria.where(predicates);

		TypedQuery<Usuario> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(nome, email, idConstrutora));
	}

}