package br.com.cabaret.CarebearBot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Parametro {
	
	@Id
	@Column
	private Long id;
	@Column
	private Double taxaMining;
}
