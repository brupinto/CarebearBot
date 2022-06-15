package br.com.cabaret.CarebearBot.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZKillDto {
	private List<ZKillAttackerDto> attackers;
	private ZKillCharacterDto victim;
	private ZkillZkbDto zkb;
	
	public Boolean ValidCorp(Long corpId) {
		Boolean rtn = false;
		
		for(ZKillAttackerDto p : attackers) {
			if (p.getCorporation_id().longValue() == corpId.longValue()) {
				rtn = true;
				break;
			}
		}
		
		if (victim.getCorporation_id().longValue() == corpId.longValue())
			rtn = true;
		
		return rtn;
	}
}
