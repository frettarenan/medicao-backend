package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.Contrato;
import com.br.projetomedicao.medicaobackend.model.ContratoMedicao;
import com.br.projetomedicao.medicaobackend.repository.MedicaoRepository;

@RestController
@RequestMapping("/medicoes")
public class MedicaoResource {

	@Autowired
	private MedicaoRepository medicaoRepository;
	
	@GetMapping("/status/ativo")
	@PreAuthorize("isAuthenticated()")
	public List<ContratoMedicao> listarStatusAtivoPorContrato(@RequestParam(required = true) Long idContrato) {
		Contrato contrato = new Contrato();
		contrato.setId(idContrato);
		return medicaoRepository.findByContrato(contrato);
	}

}