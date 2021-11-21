package br.com.staroski.dsc.integrations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.staroski.dsc.dto.Pessoa;

@FeignClient(name = "BaseAService", url = "${integration.baseA.host}")
public interface IntegrationA {

	@GetMapping("${integration.baseA.get.pessoa}")
	public Pessoa getPessoa(@PathVariable("cpf") String cpf);
}