package br.com.cabaret.CarebearBot.dto;

import java.math.BigDecimal;

public class ReportMiningDto {
	private String characterName;
	private String typeName;
	private BigDecimal pcMining;
	private Long qtMining;
	private Long taxaCorp;
	private Long fgChar;
	private Integer flNotMember;
	
	public ReportMiningDto() {
		
	}
	
	public ReportMiningDto(String characterName, String typeName, Long qtMining, Long taxaCorp, Long fgChar, Integer flNotMember) {
		super();
		this.characterName = characterName;
		this.typeName = typeName;
		this.qtMining = qtMining;
		this.taxaCorp = taxaCorp;
		this.fgChar = fgChar;
		this.flNotMember = flNotMember;
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
	public Long getFgChar() {
		return fgChar;
	}
	public void setFgChar(Long fgChar) {
		this.fgChar = fgChar;
	}
	public BigDecimal getPcMining() {
		return pcMining;
	}
	public void setPcMining(BigDecimal pcMining) {
		this.pcMining = pcMining;
	}
	public Integer getFlNotMember() {
		return flNotMember;
	}
	public void setFlNotMember(Integer flNotMember) {
		this.flNotMember = flNotMember;
	}
}
