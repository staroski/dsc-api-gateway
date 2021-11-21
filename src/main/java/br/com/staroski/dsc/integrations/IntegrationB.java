package br.com.staroski.dsc.integrations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.staroski.dsc.dto.Score;

@FeignClient(name = "BaseBService", url = "${integration.baseB.host}")
public interface IntegrationB {

	@GetMapping("${integration.baseB.get.score}")
	public Score getScoreByCpf(@PathVariable("cpf") String cpf);
}
