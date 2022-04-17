package br.com.cabaret.CarebearBot.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CorpMember {
	
	public CorpMember() {
		
	}
	public CorpMember(Long character_id) {
		characterId = character_id; 
	}
	
	@Id
	@Column
	private Long characterId;
	@Column
	private String characterName;
	@Column
	private LocalDateTime dtLastUpdate;
}
