package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.Obra;
import com.br.projetomedicao.medicaobackend.repository.ContratoRepository;

@RestController
@RequestMapping("/contratos")
public class ContratoResource {

	@Autowired
	private ContratoRepository contratoRepository;
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<Contrato> listarStatusAtivoPorObra(@RequestParam(required = true) Long idObra) {
		Obra obra = new Obra();
		obra.setId(idObra);
		return contratoRepository.findByAtivoAndObra(true, obra);
	}

}