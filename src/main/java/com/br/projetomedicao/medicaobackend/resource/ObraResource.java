package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Construtora;
import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.repository.ObraRepository;

@RestController
@RequestMapping("/obras")
public class ObraResource {

	@Autowired
	private ObraRepository obraRepository;
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Obra> listarStatusAtivoPorConstrutora(@RequestParam(required = true) Long idConstrutora) {
		Construtora construtora = new Construtora();
		construtora.setId(idConstrutora);
		return obraRepository.findByAtivoAndConstrutora(true, construtora);
	}

}