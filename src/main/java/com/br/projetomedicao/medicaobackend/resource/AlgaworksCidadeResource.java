package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.AlgaworksCidade;
import com.br.projetomedicao.medicaobackend.repository.CidadeRepository;

//@RestController
//@RequestMapping("/cidades")
public class AlgaworksCidadeResource {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<AlgaworksCidade> pesquisar(@RequestParam Long idEstado) {
		return cidadeRepository.findByEstadoId(idEstado);
	}

}