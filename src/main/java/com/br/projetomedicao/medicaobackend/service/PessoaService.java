package com.br.projetomedicao.medicaobackend.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.Pessoa;
import com.br.projetomedicao.medicaobackend.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa salvar(Pessoa pessoa) {
		pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
		return pessoaRepository.save(pessoa);
	}

	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaBD = buscarPessoaPeloId(id);
		
		pessoaBD.getContatos().clear();
		pessoaBD.getContatos().addAll(pessoa.getContatos());
		pessoaBD.getContatos().forEach(c -> c.setPessoa(pessoaBD));
		
		BeanUtils.copyProperties(pessoa, pessoaBD, "id", "contatos");
		return pessoaRepository.save(pessoaBD);
	}

	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Pessoa pessoaBD = buscarPessoaPeloId(id);
		pessoaBD.setAtivo(ativo);
		pessoaRepository.save(pessoaBD);
	}
	
	public Pessoa buscarPessoaPeloId(Long id) {
		Optional<Pessoa> pessoaBD = pessoaRepository.findById(id);
		if (!pessoaBD.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaBD.get();
	}
	
}