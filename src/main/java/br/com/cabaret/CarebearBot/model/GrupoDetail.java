package br.com.cabaret.CarebearBot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GrupoDetail {
	
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@ManyToOne
	private Grupo grupo;
	@ManyToOne
	private CorpMember member;
	@Column
	private String characterName;
}
