package br.com.cabaret.CarebearBot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZKillAttackerDto {
	private Long character_id;
	private Long corporation_id;
	private Float damage_done;
	private Boolean final_blow;
	private Float security_status;
	private Long ship_type_id;
	private Long weapon_type_id;
}
