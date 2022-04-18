package br.com.cabaret.CarebearBot.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResultReportMiningDto {
	private String CharacterName;
	private List<ResultReportMiningDetailDto> ores;
}
