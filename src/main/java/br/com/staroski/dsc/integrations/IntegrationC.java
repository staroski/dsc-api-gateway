package br.com.staroski.dsc.integrations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.staroski.dsc.dto.Evento;

@FeignClient(name = "BaseCService", url = "${integration.baseC.host}")
public interface IntegrationC {

	@GetMapping("${integration.baseC.get.evento}")
	public Evento getEventoByCpf(@PathVariable("cpf") String cpf);
}
