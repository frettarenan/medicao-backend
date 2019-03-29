package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.UnidadeMedida;
import com.br.projetomedicao.medicaobackend.repository.UnidadeMedidaRepository;

@RestController
@RequestMapping("/unidades-medida")
public class UnidadeMedidaResource {

	@Autowired
	private UnidadeMedidaRepository unidadeMedidaRepository;
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<UnidadeMedida> listarStatusAtivo() {
		return unidadeMedidaRepository.findByAtivo(true);
	}

}