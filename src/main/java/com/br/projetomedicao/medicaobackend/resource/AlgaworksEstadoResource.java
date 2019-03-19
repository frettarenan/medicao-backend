package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.AlgaworksEstado;
import com.br.projetomedicao.medicaobackend.repository.AlgaworksEstadoRepository;

//@RestController
//@RequestMapping("/estados")
public class AlgaworksEstadoResource {
	
	@Autowired
	private AlgaworksEstadoRepository estadoRepository;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<AlgaworksEstado> listar() {
		return estadoRepository.findAll();
	}
}