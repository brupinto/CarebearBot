package br.com.cabaret.CarebearBot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultReportMiningDetailDto {
	private String TypeName;
	private Long qtMining;
	private Long taxaCorp;
	private Long fgChar;

}
