package br.com.cabaret.CarebearBot.service.dto;

import br.com.cabaret.CarebearBot.model.GrupoDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupDetailDto {
	private String characterName;
	private Long characterId;

	public static GroupDetailDto toDto(GrupoDetail p) {
		return new GroupDetailDto(p.getCharacterName(), p.getMember().getCharacterId());
	}

}
