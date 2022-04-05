package br.com.cabaret.CarebearBot.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url="${api-eveonline}/corporations/", name = "corporation")
public interface CorporationClient {
	
	@GetMapping(value = "{corporation_id}/members/")
	public List<Long> characters(@PathVariable Long corporation_id, @RequestHeader("Authorization") String authorizationToken);
}
