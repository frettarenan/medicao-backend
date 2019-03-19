package com.br.projetomedicao.medicaobackend.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.projetomedicao.medicaobackend.model.AlgaworksPessoa;
import com.br.projetomedicao.medicaobackend.repository.AlgaworksPessoaRepository;

@Service
public class AlgaworksPessoaService {
	
	@Autowired
	private AlgaworksPessoaRepository pessoaRepository;
	
	public AlgaworksPessoa salvar(AlgaworksPessoa pessoa) {
		pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
		return pessoaRepository.save(pessoa);
	}

	public AlgaworksPessoa atualizar(Long id, AlgaworksPessoa pessoa) {
		AlgaworksPessoa pessoaBD = buscarPessoaPeloId(id);
		
		pessoaBD.getContatos().clear();
		pessoaBD.getContatos().addAll(pessoa.getContatos());
		pessoaBD.getContatos().forEach(c -> c.setPessoa(pessoaBD));
		
		BeanUtils.copyProperties(pessoa, pessoaBD, "id", "contatos");
		return pessoaRepository.save(pessoaBD);
	}

	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		AlgaworksPessoa pessoaBD = buscarPessoaPeloId(id);
		pessoaBD.setAtivo(ativo);
		pessoaRepository.save(pessoaBD);
	}
	
	public AlgaworksPessoa buscarPessoaPeloId(Long id) {
		Optional<AlgaworksPessoa> pessoaBD = pessoaRepository.findById(id);
		if (!pessoaBD.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaBD.get();
	}
	
}