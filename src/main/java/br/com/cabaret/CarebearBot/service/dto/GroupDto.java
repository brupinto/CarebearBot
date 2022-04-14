package br.com.cabaret.CarebearBot.service.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.cabaret.CarebearBot.model.Grupo;
import br.com.cabaret.CarebearBot.model.GrupoDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupDto {
	private Long grupoId;
	private String grupoName;
	private List<GroupDetailDto> details;
	
	
	public static GroupDto toDto(Grupo p) {
		return new GroupDto(p.getId(), p.getGrupoName(), detailToDto(p.getAlts()));
	}
	
	private static List<GroupDetailDto> detailToDto(List<GrupoDetail> alts){
		return alts.stream().map(p -> GroupDetailDto.toDto(p)).collect(Collectors.toList());
	}
}
