package br.com.cabaret.CarebearBot.dto;

public class ReportMiningDto {
	private String characterName;
	private String typeName;
	private Long qtMining;
	private Long taxaCorp;
	
	public ReportMiningDto() {
		
	}
	
	public ReportMiningDto(String characterName, String typeName, Long qtMining, Long taxaCorp) {
		super();
		this.characterName = characterName;
		this.typeName = typeName;
		this.qtMining = qtMining;
		this.taxaCorp = taxaCorp;
	}
	
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getQtMining() {
		return qtMining;
	}
	public void setQtMining(Long qtMining) {
		this.qtMining = qtMining;
	}
	public Long getTaxaCorp() {
		return taxaCorp;
	}
	public void setTaxaCorp(Long taxaCorp) {
		this.taxaCorp = taxaCorp;
	}
}
