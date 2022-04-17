package br.com.cabaret.CarebearBot.model;

import java.time.LocalDate;

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
public class MiningObserverHistory {
	
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column
	private Long recorded_corporation_id;
	@ManyToOne
	private MiningObserver observer;
	@ManyToOne
	private CorpMember corpMember;
	@Column
	private LocalDate last_updated;
	@Column
	private Long quantity;
	@Column
    private Long type_id;
	@Column
    private String typeDescription;
	@Column
	private Long page;
}
