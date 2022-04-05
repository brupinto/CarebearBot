package br.com.cabaret.CarebearBot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.cabaret.CarebearBot.client.dto.CharacterDto;

@FeignClient(url="${api-eveonline}characters/", name = "character")
public interface CharacterClient {
	
	@GetMapping(value = "{character_id}/")
	public CharacterDto characters(@PathVariable Long character_id);
}
