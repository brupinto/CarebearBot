package br.com.cabaret.CarebearBot.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.cabaret.CarebearBot.client.dto.MiningObserversDetailDto;
import br.com.cabaret.CarebearBot.client.dto.MiningObserversDto;

@FeignClient(url="${api-eveonline}", name = "industry")
public interface IndustryClient {
	
	@GetMapping("/corporation/{corporation_id}/mining/observers/")
	public List<MiningObserversDto> miningObservers(@PathVariable Long corporation_id, @RequestHeader("Authorization") String authorizationToken);
	
	@GetMapping("/corporation/{corporation_id}/mining/observers/{observer_id}/")
	public List<MiningObserversDetailDto> miningObserversDetail(@PathVariable Long corporation_id, @PathVariable Long observer_id, @RequestHeader("Authorization") String authorizationToken, @RequestHeader("page") Long page);
}
