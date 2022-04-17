package br.com.cabaret.CarebearBot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cabaret.CarebearBot.model.MiningObserverHistory;

public interface MiningObserverHistoryRepository  extends JpaRepository<MiningObserverHistory, Long> {

	@Query("select max(g.page) from MiningObserverHistory g where g.observer.observer_id = :id")
	public Long getMaxPage(@Param("id") Long observer_id);
	
	@Query("select g from MiningObserverHistory g where g.observer.observer_id = :observerId and g.corpMember.characterId = :characterId and g.type_id = :typeId and g.last_updated = :lastUpdate")
	public MiningObserverHistory searchData(@Param("observerId") Long observerId, @Param("characterId") Long characterId, @Param("typeId") Long typeId, @Param("lastUpdate") LocalDate lastUpdate);
	
	@Query("select distinct g.type_id from MiningObserverHistory g where g.typeDescription is null")
	public List<Long> distinctType();
	
	@Query("select g from MiningObserverHistory g where g.type_id = :typeId")
	public List<MiningObserverHistory> findByTypeId(@Param("typeId") Long typeId);
	
}
