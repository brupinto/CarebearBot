package br.com.cabaret.CarebearBot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TypeEve {
	@Id
	@Column
	private Long type_id;
	@Column
	private String name;
}
