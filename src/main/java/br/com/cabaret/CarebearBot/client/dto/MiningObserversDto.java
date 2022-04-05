package br.com.cabaret.CarebearBot.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MiningObserversDto {
	private String last_updated;
    private Long  observer_id;
    private String observer_type;
}
