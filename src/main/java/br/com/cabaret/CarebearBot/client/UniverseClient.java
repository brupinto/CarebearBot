package br.com.cabaret.CarebearBot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.cabaret.CarebearBot.client.dto.TypeDto;

@FeignClient(url="${api-eveonline}", name = "universe")
public interface UniverseClient {
	
	@GetMapping("/universe/types/{type_id}")
	public TypeDto typeDetail(@PathVariable Long type_id);
	
}
