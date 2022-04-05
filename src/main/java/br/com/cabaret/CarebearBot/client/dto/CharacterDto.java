package br.com.cabaret.CarebearBot.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharacterDto {
	private Long alliance_id;
	private String birthday;
	private String name;
	private Long corporation_id;
}
