package com.br.projetomedicao.medicaobackend.repository.contrato;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Contrato_;
import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.model.Obra_;

public class ContratoRepositoryImpl implements ContratoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	private Long total(String numero, String descricao, Long idContrutora, Long idObra) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Contrato> root = criteria.from(Contrato.class);
		Join<Contrato, Obra> obras = root.join(Contrato_.obra);

		Predicate[] predicates = criarRestricoes(numero, descricao, idContrutora, idObra, builder, root, obras);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private Predicate[] criarRestricoes(String numero, String descricao, Long idContrutora, Long idObra, CriteriaBuilder builder,
			Root<Contrato> root, Join<Contrato, Obra> obras) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(numero)) {
			predicates.add(builder.like(builder.lower(root.get(Contrato_.numero)), "%" + numero + "%"));
		}

		if (!StringUtils.isEmpty(descricao)) {
			predicates.add(builder.like(builder.lower(root.get(Contrato_.descricao)), "%" + descricao + "%"));
		}
		
		if (idContrutora != null) {
			predicates.add(builder.equal(obras.get(Obra_.construtora), idContrutora));
		}

		if (idObra != null) {
			predicates.add(builder.equal(root.get(Contrato_.obra), idObra));
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
	public Page<Contrato> pesquisar(String numero, String descricao, Long idContrutora, Long idObra, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Contrato> criteria = builder.createQuery(Contrato.class);
		Root<Contrato> root = criteria.from(Contrato.class);
		Join<Contrato, Obra> obras = root.join(Contrato_.obra); 

		Predicate[] predicates = criarRestricoes(numero, descricao, idContrutora, idObra, builder, root, obras);
		criteria.where(predicates);

		TypedQuery<Contrato> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(numero, descricao, idContrutora, idObra));
	}

}