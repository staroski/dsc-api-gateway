package br.com.staroski.dsc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.staroski.dsc.dto.Evento;
import br.com.staroski.dsc.dto.Pessoa;
import br.com.staroski.dsc.dto.Score;
import br.com.staroski.dsc.integrations.IntegrationA;
import br.com.staroski.dsc.integrations.IntegrationB;
import br.com.staroski.dsc.integrations.IntegrationC;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/dsc")
public class DSCController {

	@Autowired
	private final IntegrationA serviceA;

	@Autowired
	private final IntegrationB serviceB;

	@Autowired
	private final IntegrationC serviceC;

	@GetMapping("/pessoa/{cpf}")
	public Pessoa getPessoa(@PathVariable("cpf") String cpf) {
		return serviceA.getPessoa(cpf);
	}

	@GetMapping("/score/{cpf}")
	public Score getScoreByCpf(@PathVariable("cpf") String cpf) {
		return serviceB.getScoreByCpf(cpf);
	}

	@GetMapping("/evento/{cpf}")
	public Evento getEventoByCpf(@PathVariable("cpf") String cpf) {
		return serviceC.getEventoByCpf(cpf);
	}
}
