package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.UnidadeMedida;
import com.br.projetomedicao.medicaobackend.service.UnidadeMedidaService;

@RestController
@RequestMapping("/unidades-medidas")
public class UnidadeMedidaResource {

	@Autowired
	private UnidadeMedidaService unidadeMedidaService;
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<UnidadeMedida> listarUnidadesMedidasComStatusAtivo() {
		return unidadeMedidaService.listarUnidadesMedidasComStatusAtivo();
	}

}