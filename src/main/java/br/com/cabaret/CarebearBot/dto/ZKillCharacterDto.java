package br.com.cabaret.CarebearBot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZKillCharacterDto {
	private Long alliance_id;
	private Long character_id;
	private Long corporation_id;
	private Long damage_done;
	private Long damage_taken;
	private Long ship_type_id;
}
