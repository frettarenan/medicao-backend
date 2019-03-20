package com.br.projetomedicao.medicaobackend.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetomedicao.medicaobackend.model.ContratoMedicao;
import com.br.projetomedicao.medicaobackend.model.ContratoServico;
import com.br.projetomedicao.medicaobackend.repository.MedicaoRepository;
import com.br.projetomedicao.medicaobackend.repository.ServicoRepository;

@RestController
@RequestMapping("/servicos")
public class ServicoResource {

	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private MedicaoRepository medicaoRepository;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<ContratoServico> listarGruposPorMedicao(@RequestParam(required = true) Long idMedicao) {
		ContratoMedicao contratoMedicao = medicaoRepository.findById(idMedicao).get();
		return servicoRepository.findByContrato(contratoMedicao.getContrato());
	}

}