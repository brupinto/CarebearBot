package br.com.cabaret.CarebearBot.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MiningObserversDetailDto {
	private Long character_id;
    private String last_updated;
    private Long quantity;
    private Long recorded_corporation_id;
    private Long type_id;
}
