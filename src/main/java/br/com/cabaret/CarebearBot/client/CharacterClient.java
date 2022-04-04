package br.com.cabaret.CarebearBot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.cabaret.CarebearBot.client.dto.VerifyDto;

@FeignClient(url="${api-eveonline-auth}/characters/", name = "character")
public interface CharacterClient {
	
	@GetMapping(value = "{character_id}/")
	public VerifyDto verify(@PathVariable Long character_id, @RequestHeader("Authorization") String authorizationToken);
	
}
