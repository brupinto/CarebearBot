package br.com.cabaret.CarebearBot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.cabaret.CarebearBot.client.dto.MiningObserversDto;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MiningObserver {
	
	@Id 
	@Column
    private Long  observer_id;
	@Column
	private String observer_type;
	
	public static MiningObserver parse(MiningObserversDto p) {
		MiningObserver rtn = new MiningObserver();
		rtn.setObserver_id(p.getObserver_id());
		rtn.setObserver_type(p.getObserver_type());
		return rtn;
	}
}
